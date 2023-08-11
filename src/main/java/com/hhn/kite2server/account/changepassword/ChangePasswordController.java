package com.hhn.kite2server.account.changepassword;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"changepassword"})
@AllArgsConstructor
public class ChangePasswordController {

    private final ChangePasswordService accountService;

    @PostMapping
    public Response changePassword(@AuthenticationPrincipal AppUser user, @RequestBody ChangePasswordRequest request) {
        Response response = new Response();
        boolean changed = accountService.changePassword(user, request.getOldPassword(), request.getNewPassword());
        if (changed) {
            response.setResultCode(ResultCode.SUCCESSFULLY_CHANGED_PASSWORD.toInt());
            response.setResultText(ResultCode.SUCCESSFULLY_CHANGED_PASSWORD.toString());
        } else {
            response.setResultCode(ResultCode.CHANGE_OF_PASSWORD_FAILED.toInt());
            response.setResultText(ResultCode.CHANGE_OF_PASSWORD_FAILED.toString());
        }
        return response;
    }
}