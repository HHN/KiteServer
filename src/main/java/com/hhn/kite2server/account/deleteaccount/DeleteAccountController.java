package com.hhn.kite2server.account.deleteaccount;

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

    private final DeleteAccountService accountService;

    @DeleteMapping
    public Response deleteAccount(@AuthenticationPrincipal AppUser user) {
        Response response = new Response();
        ResultCode code = accountService.delete(user);
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }
}
