package main.project4.RMI.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Learner interface defines the methods for learners in the Paxos consensus algorithm,
 * allowing them to learn accepted values for proposals.
 */
public interface Learner extends Remote {

    /**
     * Notifies the learner about an accepted value for a proposal.
     *
     * @param proposalId     The unique identifier of the proposal.
     * @param acceptedValue  The accepted value for the proposal.
     * @throws RemoteException If a remote communication error occurs.
     */
    void learn(String proposalId, Operation acceptedValue) throws RemoteException;
}
