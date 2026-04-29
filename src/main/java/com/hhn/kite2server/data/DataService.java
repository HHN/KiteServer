package com.hhn.kite2server.data;

import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing the lifecycle of interaction data, 
 * including retrieval, creation, and deletion.
 */
@RequiredArgsConstructor
@Service
public class DataService {
    private final DataRepository dataRepository;

    public Response getAllDataObjects() {
        List<DataObject> dataObjects = dataRepository.findAll();
        return Response.successWithData(ResultCode.SUCCESSFULLY_GOT_ALL_DATA_OBJECTS, dataObjects);
    }

    public Response addDataObject(AddDataObjectRequest request) {
        DataObject dataObject = new DataObject();
        dataObject.setCompletion(request.completion());
        dataObject.setPrompt(request.prompt());
        saveDataObject(dataObject);
        return Response.success(ResultCode.SUCCESSFULLY_ADDED_DATA_OBJECT);
    }

    public Response removeDataObject(RemoveDataObjectRequest request) {
        boolean success = deleteDataObject(request.id());
        ResultCode code = success ? ResultCode.SUCCESSFULLY_DELETED_DATA_OBJECT : ResultCode.NO_SUCH_DATA_OBJECT;
        return Response.success(code);
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
