package com.hhn.kite2server.logout;

import com.hhn.kite2server.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "userlogout")
@AllArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping
    public Response logout(HttpServletRequest request) {
        return logoutService.logout(request);
    }
}
