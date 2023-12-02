package main.project4.RMI.ProcessRequest;

/**
 * The processRequest class represents a response to a request made to the RMI server.
 */

public class processRequest {

    /**
     * Constructs a processRequest object with the specified status, message, and value.
     *
     * @param status  The status of the request (true if successful, false otherwise).
     * @param message A message providing additional information about the request.
     * @param value   The value associated with the request, if applicable.
     */
    public processRequest(Boolean status, String message, String value) {
        this.status = status;
        this.message = message;
        this.value = value;
    }

    /**
     * The status of the request.
     */
    public Boolean status;

    /**
     * A message providing additional information about the request.
     */
    public String message;

    /**
     * The value associated with the request, if applicable.
     */
    public String value;
}
