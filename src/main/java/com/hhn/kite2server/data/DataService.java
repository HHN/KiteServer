package com.hhn.kite2server.data;

import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.response.ResultCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DataService {
    private final DataRepository dataRepository;

    public Response getAllDataObjects() {
        Response response = new Response();
        List<DataObject> dataObjects = dataRepository.findAll();
        response.setDataObjects(dataObjects);
        ResultCode code = ResultCode.SUCCESSFULLY_GOT_ALL_DATA_OBJECTS;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public Response addDataObject(AddDataObjectRequest request) {
        DataObject dataObject = new DataObject();
        dataObject.setCompletion(request.getCompletion());
        dataObject.setPrompt(request.getPrompt());
        saveDataObject(dataObject);
        Response response = new Response();
        ResultCode code = ResultCode.SUCCESSFULLY_ADDED_DATA_OBJECT;
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public Response removeDataObject(RemoveDataObjectRequest request) {
        Response response = new Response();
        boolean success = deleteDataObject(request.getId());

        ResultCode code;

        if (success) {
            code = ResultCode.SUCCESSFULLY_DELETED_DATA_OBJECT;
        } else {
            code = ResultCode.NO_SUCH_DATA_OBJECT;
        }
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    public boolean deleteDataObject(Long id) {
        if (dataRepository.findById(id).isEmpty()) {
            return false;
        }
        dataRepository.deleteById(id);
        return true;
    }

    public void saveDataObject(DataObject dataObject) {
        if (dataObject == null) {
            return;
        }
        dataRepository.save(dataObject);
    }
}
