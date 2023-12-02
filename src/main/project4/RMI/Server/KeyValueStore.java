package main.project4.RMI.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The KeyValueStore interface defines the methods for interacting with a key-value store
 * using remote method invocation (RMI).
 */
public interface KeyValueStore extends Remote {

    /**
     * Puts a key-value pair into the store.
     *
     * @param key   The key to be inserted.
     * @param value The value associated with the key.
     * @return A message indicating the result of the put operation.
     * @throws RemoteException      If a remote communication error occurs.
     * @throws InterruptedException If the operation is interrupted while waiting.
     */
    String put(String key, String value) throws RemoteException, InterruptedException;

    /**
     * Retrieves the value associated with a given key.
     *
     * @param key The key to look up.
     * @return The value associated with the key, or a message indicating its absence.
     * @throws RemoteException      If a remote communication error occurs.
     * @throws InterruptedException If the operation is interrupted while waiting.
     */
    String get(String key) throws RemoteException, InterruptedException;

    /**
     * Deletes a key-value pair from the store based on the key.
     *
     * @param key The key to delete.
     * @return A message indicating the result of the delete operation.
     * @throws RemoteException      If a remote communication error occurs.
     * @throws InterruptedException If the operation is interrupted while waiting.
     */
    String delete(String key) throws RemoteException, InterruptedException;

    /**
     * Checks if the store contains a specific key.
     *
     * @param key The key to check.
     * @return True if the store contains the key, false otherwise.
     * @throws RemoteException      If a remote communication error occurs.
     * @throws InterruptedException If the operation is interrupted while waiting.
     */
    Boolean containsKey(String key) throws RemoteException, InterruptedException;
}
