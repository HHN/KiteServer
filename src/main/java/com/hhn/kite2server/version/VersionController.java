package com.hhn.kite2server.version;

import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.response.ResultCode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "version")
@AllArgsConstructor
public class VersionController {

    private static int VERSION = 11;

    @GetMapping
    public Response getVersion() {
        Response response = new Response();
        response.setVersion(VERSION);
        response.setResultCode(ResultCode.SUCCESSFULLY_GOT_VERSION.toInt());
        response.setResultText(ResultCode.SUCCESSFULLY_GOT_VERSION.toString());
        return response;
    }
}
