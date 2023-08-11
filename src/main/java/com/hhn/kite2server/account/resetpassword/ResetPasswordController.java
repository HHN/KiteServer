package com.hhn.kite2server.account.resetpassword;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"resetpassword", "resetpassword/**"})
@AllArgsConstructor
public class ResetPasswordController {

    private final ResetPasswordService accountService;

    @PostMapping
    public Response resetPassword(@RequestBody ResetPasswordRequest request) {
        Response response = new Response();
        AppUser user;

        if (accountService.findByEmail(request.getUsername().toLowerCase()).isPresent()) {
            user = accountService.findByEmail(request.getUsername().toLowerCase()).get();

        } else if (accountService.findByUsername(request.getUsername().toLowerCase()).isPresent()) {
            user = accountService.findByUsername(request.getUsername().toLowerCase()).get();

        } else {
            response.setResultCode(ResultCode.USER_NOT_FOUND.toInt());
            response.setResultText(ResultCode.USER_NOT_FOUND.toString());
            return response;
        }
        ResultCode code = accountService.sendResetConfirmationEmail(user);
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }

    @GetMapping
    public Response confirm(@RequestParam("token") String token) {
        Response response = new Response();
        ResultCode resultCode = accountService.confirmPasswordReset(token);
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }
}
