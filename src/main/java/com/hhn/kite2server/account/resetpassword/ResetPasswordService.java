package com.hhn.kite2server.account.resetpassword;

import com.hhn.kite2server.appuser.AppUserRepository;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.appuser.AppUserService;
import com.hhn.kite2server.email.EmailCreatorService;
import com.hhn.kite2server.email.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ResetPasswordService {

    private final AppUserService appUserService;
    private final EmailSender emailSender;
    private final ResetPasswordTokenRepository resetPasswordConfirmationTokenRepository;
    private final EmailCreatorService emailCreatorService;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResultCode resetPassword(AppUser user) {
        if (user == null) {
            return ResultCode.USER_NOT_FOUND;
        }
        String newPassword = PasswordGenerator.generatePassword();
        changePassword(user, newPassword);

        emailSender.send(user.getEmail(), emailCreatorService.buildEmailInformAboutNewPassword(newPassword), "Neues Passwort");
        return ResultCode.SUCCESSFULLY_RESETED_PASSWORD;
    }

    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserService.findByUsername(username);
    }

    public ResultCode confirmPasswordReset(String token) {
        boolean tokenExists = getToken(token).isPresent();
        if (!tokenExists) {
            return ResultCode.TOKEN_NOT_FOUND;
        }
        ResetPasswordToken confirmationToken = getToken(token).get();
        if (confirmationToken.getConfirmedAt() != null) {
            return ResultCode.PASSWORD_ALREADY_RESETED;
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            return ResultCode.TOKEN_EXPIRED;
        }
        setConfirmedAt(token);
        resetPassword(confirmationToken.getAppUser());
        return ResultCode.SUCCESSFULLY_RESETED_PASSWORD;
    }

    public int setConfirmedAt(String token) {
        return resetPasswordConfirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public Optional<ResetPasswordToken> getToken(String token) {
        return resetPasswordConfirmationTokenRepository.findByToken(token);
    }

    public ResultCode sendResetConfirmationEmail(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        ResetPasswordToken confirmationToken =
                new ResetPasswordToken(token, LocalDateTime.now(), LocalDateTime.now().plusDays(7), appUser);
        resetPasswordConfirmationTokenRepository.save(confirmationToken);

        String link = "http://localhost:8080/resetpassword/confirm?token=" + token;
        emailSender.send(appUser.getEmail(), emailCreatorService.buildEmailConfirmResetOfPassword(link), "Passwort Zurücksetzung");
        return ResultCode.SUCCESSFULLY_INITIATED_RESET;
    }

    public void deleteAllTokensOfUser(long id) {
        resetPasswordConfirmationTokenRepository.deleteTokensFromUser(id);
    }

    public boolean changePassword(AppUser user, String newPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        appUserRepository.save(user);
        return true;
    }
}
