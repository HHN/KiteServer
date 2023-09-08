package com.hhn.kite2server.money;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MoneyService {

    private final MoneyRepository moneyRepository;

    public Response getMoney(AppUser user, GetMoneyRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_GET_MONEY;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }

        Optional<Money> optionalMoney = moneyRepository.findByUser((user));

        if (optionalMoney.isPresent()) {
            response.setMoney(optionalMoney.get().getValue());
            ResultCode code = ResultCode.SUCCESSFULLY_GOT_MONEY;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        } else {
            ResultCode code = ResultCode.FAILED_TO_GET_MONEY;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }
        return response;
    }

    public Response addToMoney(AppUser user, AddMoneyRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_UPDATE_MONEY;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }

        Optional<Money> optionalMoney = moneyRepository.findByUser((user));

        if (optionalMoney.isPresent()) {
            Money money = optionalMoney.get();
            money.setValue(money.getValue() + request.getValue());
            moneyRepository.save(money);
            response.setMoney(money.getValue());
            ResultCode code = ResultCode.SUCCESSFULLY_UPDATED_MONEY;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());

        } else {
            ResultCode code = ResultCode.FAILED_TO_UPDATE_MONEY;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }
        return response;
    }

    public void deleteMoneyByUser(AppUser appUser) {
        if (appUser == null) {
            return;
        }
        moneyRepository.deleteByUser(appUser);
    }

    public void saveMoney(Money money) {
        if (money == null) {
            return;
        }
        moneyRepository.save(money);
    }
}
