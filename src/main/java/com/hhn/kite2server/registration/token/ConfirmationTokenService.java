package com.hhn.kite2server.registration.token;

import com.hhn.kite2server.appuser.AppUserService;
import com.hhn.kite2server.common.ResultCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }


    @Transactional
    public ResultCode confirmToken(String token, AppUserService appUserService) {
        boolean tokenExists = getToken(token).isPresent();

        if (!tokenExists) {
            return ResultCode.TOKEN_NOT_FOUND;
        }

        ConfirmationToken confirmationToken = getToken(token).get();

        if (confirmationToken.getConfirmedAt() != null) {
            return ResultCode.EMAIL_ALREADY_CONFIRMED;
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            return ResultCode.TOKEN_EXPIRED;
        }

        setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getId());
        return ResultCode.SUCCESSFULLY_CONFIRMED;
    }
}