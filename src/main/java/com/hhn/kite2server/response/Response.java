package com.hhn.kite2server.response;

import com.hhn.kite2server.data.DataObject;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int resultCode;
    private String resultText;
    private String completion;
    private List<DataObject> dataObjects;
    private int version;
    private int userRole;

    // Factory methods for common responses
    public static Response success(ResultCode code) {
        Response response = new Response();
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public static Response success(ResultCode code, String completion) {
        Response response = success(code);
        response.setCompletion(completion);
        return response;
    }

    public static Response successWithData(ResultCode code, List<DataObject> dataObjects) {
        Response response = success(code);
        response.setDataObjects(dataObjects);
        return response;
    }

    public static Response error(ResultCode code) {
        Response response = new Response();
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public static Response error(ResultCode code, String customText) {
        Response response = error(code);
        response.setResultText(customText);
        return response;
    }

    public static Response successWithText(ResultCode code, String completion, String customText) {
        Response response = success(code, completion);
        response.setResultText(customText);
        return response;
    }
}
