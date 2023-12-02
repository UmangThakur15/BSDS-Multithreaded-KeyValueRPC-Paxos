package main.project4.RMI.Server;

/**
 * The Operation class represents an operation in the Paxos consensus algorithm.
 * It encapsulates the type of operation (PUT or DELETE), associated key, and value (for PUT operation).
 */
class Operation {

    /**
     * The type of operation PUT or DELETE.
     */
    String type;

    /**
     * The key associated with the operation.
     */
    String key;

    /**
     * The value associated with the operation.
     */
    String value;

    /**
     * Constructs an Operation object with the specified type, key, and value.
     *
     * @param type  The type of operation.
     * @param key   The key associated with the operation.
     * @param value The value associated with the operation.
     */
    Operation(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }
}
