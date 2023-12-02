package main.project4.RMI.Server;

/**
 * The Pair class represents a simple key-value pair.
 *
 * @param <K> The type of the key.
 * @param <T> The type of the value.
 */
class Pair<K, T> {

    private T value;
    private K key;

    /**
     * Constructs a Pair object with the specified key and value.
     *
     * @param key   The key of the pair.
     * @param value The value associated with the key.
     */
    Pair(K key, T value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the value associated with the key.
     *
     * @return The value associated with the key.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value associated with the key.
     *
     * @param value The new value to be associated with the key.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Gets the key of the pair.
     *
     * @return The key of the pair.
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets the key of the pair.
     *
     * @param key The new key for the pair.
     */
    public void setKey(K key) {
        this.key = key;
    }
}
