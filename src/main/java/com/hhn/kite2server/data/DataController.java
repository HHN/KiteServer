package com.hhn.kite2server.data;

import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing interaction data objects via standard 
 * CRUD operations.
 */
@RestController
@RequestMapping(path = "data")
@AllArgsConstructor
public class DataController {
    private final DataService dataService;

    @GetMapping
    public Response getAllDataObjects() {
        return dataService.getAllDataObjects();
    }

    @PostMapping
    public Response addDataObject(@RequestBody AddDataObjectRequest request) {
        return dataService.addDataObject(request);
    }

    @DeleteMapping
    public Response removeDataObject(@RequestBody RemoveDataObjectRequest request) {
        return dataService.removeDataObject(request);
    }
}
