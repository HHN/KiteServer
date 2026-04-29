package com.hhn.kite2server.response;

/**
 * Enumeration of application-specific result codes used to communicate 
 * the outcome of API requests to the client.
 */
public enum ResultCode {
    FAILURE(1),
    NOT_AUTHORIZED(2),
    SUCCESSFULLY_GOT_COMPLETION(3),
    SUCCESSFULLY_GOT_ALL_DATA_OBJECTS(19),
    SUCCESSFULLY_ADDED_DATA_OBJECT(20),
    SUCCESSFULLY_DELETED_DATA_OBJECT(21),
    NO_SUCH_DATA_OBJECT(22);

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    /**
     * Returns the integer representation of the result code for JSON serialization.
     * @return the integer code.
     */
    public int toInt() {
        return code;
    }
}
