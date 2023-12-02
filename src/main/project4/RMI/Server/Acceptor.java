package main.project4.RMI.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Acceptor interface defines the methods that a Paxos acceptor should implement
 * to participate in the Paxos consensus algorithm.
 */
public interface Acceptor extends Remote {

    /**
     * Prepares the acceptor for the proposal with the specified proposal ID and operation.
     * This method is called by the proposer during the prepare phase of Paxos.
     *
     * @param proposalId The unique proposal ID.
     * @param operation  The proposed operation.
     * @return True if the acceptor is prepared to accept the proposal, false otherwise.
     * @throws RemoteException If a remote communication error occurs.
     */
    Boolean prepare(String proposalId, Operation operation) throws RemoteException;

    /**
     * Accepts the proposal with the specified proposal ID and value.
     * This method is called by the proposer during the accept phase of Paxos.
     *
     * @param proposalId     The unique proposal ID.
     * @param proposalValue  The value proposed in the proposal.
     * @throws RemoteException If a remote communication error occurs.
     */
    void accept(String proposalId, Operation proposalValue) throws RemoteException;
}
