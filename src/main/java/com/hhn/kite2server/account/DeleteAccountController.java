package com.hhn.kite2server.account;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"delete"})
@AllArgsConstructor
public class DeleteAccountController {

    private final AccountService accountService;

    @DeleteMapping
    public Response deleteAccount(@AuthenticationPrincipal AppUser user, @RequestBody DeleteAccountRequest request) {
        Response response = new Response();
        if (user.getId() != request.getAccountId()) {
            response.setResultCode(ResultCode.NOT_AUTHORIZED.toInt());
            response.setResultText(ResultCode.NOT_AUTHORIZED.toString());
        } else {
            ResultCode code = accountService.delete(request);
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }
        return response;
    }
}
