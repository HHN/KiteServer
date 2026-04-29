package com.hhn.kite2server.response;

import com.hhn.kite2server.data.DataObject;
import lombok.*;

import java.util.List;

/**
 * Generic response wrapper used for all API communications.
 * It encapsulates the result status, descriptive text, session tokens, and actual data payload.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    /** Numeric code representing the result state (see ResultCode) */
    private int resultCode;
    /** Human-readable description of the result */
    private String resultText;
    /** Optional session or completion token */
    private String completion;
    /** List of data objects returned by the operation */
    private List<DataObject> dataObjects;
    /** Protocol or application version */
    private int version;
    /** The role assigned to the user in the current session */
    private int userRole;

    /**
     * Creates a successful response with the given result code.
     * @param code The ResultCode enum value.
     * @return A new Response instance.
     */
    public static Response success(ResultCode code) {
        Response response = new Response();
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    /**
     * Creates a successful response including a completion token.
     * @param code The ResultCode enum value.
     * @param completion The token or completion string.
     * @return A new Response instance.
     */
    public static Response success(ResultCode code, String completion) {
        Response response = success(code);
        response.setCompletion(completion);
        return response;
    }

    /**
     * Creates a successful response containing a list of data objects.
     * @param code The ResultCode enum value.
     * @param dataObjects The payload data.
     * @return A new Response instance.
     */
    public static Response successWithData(ResultCode code, List<DataObject> dataObjects) {
        Response response = success(code);
        response.setDataObjects(dataObjects);
        return response;
    }

    /**
     * Creates an error response based on a result code.
     * @param code The ResultCode enum value representing the error.
     * @return A new Response instance.
     */
    public static Response error(ResultCode code) {
        Response response = new Response();
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    /**
     * Creates an error response with a custom descriptive text.
     * @param code The ResultCode enum value.
     * @param customText Specific error details.
     * @return A new Response instance.
     */
    public static Response error(ResultCode code, String customText) {
        Response response = error(code);
        response.setResultText(customText);
        return response;
    }

    /**
     * Creates a successful response with a specific completion token and custom result text.
     * @param code The ResultCode enum value.
     * @param completion The session/completion token.
     * @param customText Custom success message.
     * @return A new Response instance.
     */
    public static Response successWithText(ResultCode code, String completion, String customText) {
        Response response = success(code, completion);
        response.setResultText(customText);
        return response;
    }
}
