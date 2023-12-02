package main.project4.RMI.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Proposer interface represents a proposer in the Paxos consensus algorithm.
 * Proposers are responsible for initiating the proposal phase and proposing values.
 */
public interface Proposer extends Remote {

    /**
     * Proposes a value with a given proposal ID to the acceptors.
     *
     * @param proposalId     The unique ID for the proposal.
     * @param proposalValue  The value to be proposed.
     * @throws RemoteException if a remote communication error occurs.
     * @throws InterruptedException if the operation is interrupted.
     */
    void propose(String proposalId, Operation proposalValue) throws RemoteException, InterruptedException;
}
