package com.hhn.kite2server.user_role;

import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.response.ResultCode;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "role")
@AllArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping
    public Response getCompletion(@RequestBody UserRoleRequest request) {
        Response response = new Response();
        int userRole = userRoleService.getUserRoleByCode(request.getCode());
        response.setUserRole(userRole);
        response.setResultCode(ResultCode.SUCCESSFULLY_GOT_USER_ROLE.toInt());
        response.setResultText(ResultCode.SUCCESSFULLY_GOT_USER_ROLE.toString());
        return response;
    }
}
