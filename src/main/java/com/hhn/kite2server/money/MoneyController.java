package com.hhn.kite2server.money;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "money")
@AllArgsConstructor
public class MoneyController {

    private final MoneyService moneyService;

    @GetMapping
    public Response getMoney(@AuthenticationPrincipal AppUser user, @RequestBody GetMoneyRequest request) {
        return moneyService.getMoney(user, request);
    }

    @PostMapping
    public Response addToMoney(@AuthenticationPrincipal AppUser user, @RequestBody AddMoneyRequest request) {
        return moneyService.addToMoney(user, request);
    }
}
