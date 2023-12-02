package main.project4.RMI.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Server class implements the Proposer, Acceptor, Learner, and KeyValueStore interfaces
 * and represents a server in the Paxos consensus algorithm.
 */
public class Server extends UnicastRemoteObject implements Proposer, Acceptor, Learner, KeyValueStore {

    private final ConcurrentHashMap<String, String> keyValueStore = new ConcurrentHashMap<>();
    private final Map<String, Pair<String, Operation>> containsKey;
    private Acceptor[] acceptors;
    private Learner[] learners;
    private boolean serverStatus = false;
    private long serverDownTime = 0;
    private final int serverId;
    boolean isSuccess = false;
    int serverDownTimer = 100;
    double div = 2.0;
    private final Map<String, Pair<Integer, Boolean>> stringPairMap;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");

    /**
     * Constructs a Server object with the given server ID.
     *
     * @param serverId The ID of the server.
     * @throws RemoteException if a remote communication error occurs.
     */
    public Server(int serverId) throws RemoteException {
        this.serverId = serverId;
        this.containsKey = new HashMap<>();
        this.stringPairMap = new HashMap<>();
    }

    /**
     * Sets the acceptors for this server.
     *
     * @param acceptors An array of acceptors to set.
     * @throws RemoteException if a remote communication error occurs.
     */
    public void setAcceptors(Acceptor[] acceptors) throws RemoteException {
        this.acceptors = acceptors;
    }

    /**
     * Sets the learners for this server.
     *
     * @param learners An array of learners to set.
     * @throws RemoteException if a remote communication error occurs.
     */
    public void setLearners(Learner[] learners) throws RemoteException {
        this.learners = learners;
    }

    /**
     * Puts a new key-value pair into the key-value store or updates the value of an existing key.
     *
     * @param key The key to be inserted or updated.
     * @param value The value to be associated with the key.
     * @return A message indicating the success or failure of the PUT operation.
     * @throws RemoteException if a remote communication error occurs.
     * @throws InterruptedException if the operation is interrupted while waiting for a response.
     */
    @Override
    public synchronized String put(String key, String value) throws RemoteException, InterruptedException {
        isSuccess = false;
        proposeOperation(new Operation("PUT", key, value));
        if (isSuccess)
            return "PUT operation successful for key - " + key + " with value - " + value;
        else
            return "Error for PUT operation for key -> " + key;
    }

    /**
     * Retrieves the value associated with the given key from the key-value store.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The value associated with the given key if it exists, or an error message if not found.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public synchronized String get(String key) throws RemoteException {
        if (keyValueStore.containsKey(key))
            return keyValueStore.get(key);
        return "No entry exists for the key -> " + key;
    }

    /**
     * Deletes the value associated with the given key from the key-value store.
     *
     * @param key The key whose associated value is to be deleted.
     * @return A status message indicating the result of the DELETE operation.
     * @throws RemoteException if a remote communication error occurs.
     * @throws InterruptedException if the thread is interrupted while waiting for the operation to complete.
     */
    @Override
    public synchronized String delete(String key) throws RemoteException, InterruptedException {
        isSuccess = false;
        proposeOperation(new Operation("DELETE", key, null));
        if (isSuccess)
            return "DELETE operation successful for key -> " + key;
        else
            return "Error during DELETE operation for key -> " + key;
    }

    /**
     * Checks if the key is present in the key-value store.
     *
     * @param key The key to be checked for presence in the key-value store.
     * @return {@code true} if the key is present, {@code false} if the key is not present.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public Boolean containsKey(String key) throws RemoteException {
        return keyValueStore.containsKey(key);
    }

    /**
     * Checks the status of the acceptor server to determine if it is currently down.
     * If the server was previously marked as down and the specified server down timer has elapsed,
     * the server status is updated and returned as not down.
     *
     * @return {@code true} if the server is considered down, {@code false} if it is considered operational.
     * @throws RemoteException if a remote communication error occurs.
     */
    private boolean checkAcceptorStatus() throws RemoteException {
        if (serverStatus) {
            long currentTime = System.currentTimeMillis() / 1000L;
            if (this.serverDownTime + serverDownTimer <= currentTime) {
                serverStatus = false;
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Initiates the proposal of an operation by generating a proposal ID and calling the propose method.
     * This method simplifies the process of proposing an operation during the Paxos consensus protocol.
     *
     * @param operation The operation to be proposed for acceptance.
     * @throws RemoteException if a remote communication error occurs.
     * @throws InterruptedException if the current thread is interrupted while waiting.
     */
    private void proposeOperation(Operation operation) throws RemoteException, InterruptedException {
        String proposalId = generateProposalId();
        propose(proposalId, operation);
    }

    /**
     * Prepares to accept a proposed operation value by checking necessary conditions.
     * This method is a crucial part of the Paxos consensus protocol's prepare phase.
     *
     * @param proposalId     The unique ID of the proposal.
     * @param operation      The proposed operation to be prepared for acceptance.
     * @return {@code true} if the prepare conditions are met, {@code false} otherwise.
     *         Returns {@code null} if the server status prevents participation.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public synchronized Boolean prepare(String proposalId, Operation operation) throws RemoteException {
        if (checkAcceptorStatus()) {
            return null;
        }
        if (this.containsKey.containsKey(operation.key)) {
            if (Long.parseLong(this.containsKey.get(operation.key).getKey().split(":")[1]) >
                    Long.parseLong(proposalId.split(":")[1])) {
                return false;
            }
        }
        this.containsKey.put(operation.key, new Pair<>(proposalId, operation));
        return true;
    }

    /**
     * Accepts a proposed operation value after ensuring proper conditions are met.
     * This method is a crucial part of the Paxos consensus protocol's acceptance phase.
     *
     * @param proposalId     The unique ID of the proposal.
     * @param proposalValue  The proposed operation value.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public synchronized void accept(String proposalId, Operation proposalValue) throws RemoteException {
        if (checkAcceptorStatus()) {
            return;
        }

        if (this.containsKey.containsKey(proposalValue.key)) {
            if (Long.parseLong(this.containsKey.get(proposalValue.key).getKey().split(":")[1]) <=
                    Long.parseLong(proposalId.split(":")[1])) {
                for (Learner learner : this.learners) {
                    learner.learn(proposalId, proposalValue);
                }
            }
        }
    }

    /**
     * Proposes an operation to be accepted through the Paxos consensus protocol.
     * This method initiates the prepare phase, gathers responses, and handles the acceptance phase.
     *
     * @param proposalId     The unique ID of the proposal.
     * @param proposalValue  The proposed operation value.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public synchronized void propose(String proposalId, Operation proposalValue) throws RemoteException {
        List<Boolean> prepareResponse = new ArrayList<>();
        for (Acceptor acceptor : this.acceptors) {
            Boolean res = acceptor.prepare(proposalId, proposalValue);
            prepareResponse.add(res);
        }
        int majority = 0;

        for (int i = 0; i < 5; i++) {
            if (prepareResponse.get(i) != null) {
                if (prepareResponse.get(i))
                    majority += 1;
            }
        }

        if (majority >= Math.ceil(acceptors.length / div)) {
            for (int i = 0; i < 5; i++) {
                if (prepareResponse.get(i) != null)
                    this.acceptors[i].accept(proposalId, proposalValue);
            }
        }
    }

    /**
     * Handles the learning phase of the Paxos protocol upon receiving an accepted value.
     * Updates the learner's tracking of accepted values and triggers execution of the operation if necessary.
     *
     * @param proposalId     The unique ID of the proposal associated with the accepted value.
     * @param acceptedValue  The accepted operation value.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public synchronized void learn(String proposalId, Operation acceptedValue) throws RemoteException {
        if (!this.stringPairMap.containsKey(proposalId)) {
            this.stringPairMap.put(proposalId, new Pair<>(1, false));
        } else {
            Pair<Integer, Boolean> learnerPair = this.stringPairMap.get(proposalId);
            learnerPair.setKey(learnerPair.getKey() + 1);
            if (learnerPair.getKey() >= Math.ceil(acceptors.length / div) && !learnerPair.getValue()) {
                this.isSuccess = executeOperation(acceptedValue);
                learnerPair.setValue(true);
            }
            this.stringPairMap.put(proposalId, learnerPair);
        }
    }

    /**
     * Generates a unique proposal ID combining the server's ID and the current timestamp.
     *
     * @return A unique proposal ID in the format: serverId:timestamp.
     * @throws RemoteException if a remote communication error occurs.
     */
    private String generateProposalId() throws RemoteException {
        return serverId + ":" + System.currentTimeMillis();
    }

    /**
     * Executes the specified operation on the keyValueStore.
     *
     * @param operation The operation to be executed.
     * @return True if the operation was successfully executed, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */

    private boolean executeOperation(Operation operation) throws RemoteException {
        if (operation == null) return false;
        switch (operation.type) {
            case "PUT":
                keyValueStore.put(operation.key, operation.value);
                System.out.println(getCurrentTime() + " - PUT Operation successful for Key:Value - " + operation.key + ":" + operation.value);
                return true;
            case "DELETE":
                if (keyValueStore.containsKey(operation.key)) {
                    keyValueStore.remove(operation.key);
                    System.out.println(getCurrentTime() + " - DELETE Operation successful for Key -> " + operation.key);
                    return true;
                } else {
                    System.out.println(getCurrentTime() + " - DELETE Operation Failed for Key -> " + operation.key);
                    return false;
                }
            default:
                throw new IllegalArgumentException("Unknown operation type: " + operation.type);
        }
    }

    /**
     * Sets the server status to "down" and records the timestamp.
     */
    public void setServerDown() {
        this.serverStatus = true;
        this.serverDownTime = System.currentTimeMillis() / 1000L;
    }

    /**
     * Gets the current time in a formatted string.
     *
     * @return Formatted current time string.
     */
    private String getCurrentTime() {
        return "<Time: " + dateFormat.format(new Date()) + ">";
    }
}
