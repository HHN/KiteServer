package com.hhn.kite2server.login;

import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public Response login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }
}
