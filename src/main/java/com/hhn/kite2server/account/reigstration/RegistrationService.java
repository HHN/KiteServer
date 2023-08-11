package com.hhn.kite2server.account.reigstration;

import com.hhn.kite2server.account.token.ConfirmationToken;
import com.hhn.kite2server.account.token.ConfirmationTokenService;
import com.hhn.kite2server.appuser.AppUserRepository;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.appuser.AppUserRole;
import com.hhn.kite2server.email.EmailCreatorService;
import com.hhn.kite2server.email.EmailSender;
import com.hhn.kite2server.email.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidator emailValidator;
    private final EmailSender emailSender;
    private final EmailCreatorService emailCreatorService;

    public ResultCode register(RegistrationRequest request) {
        AppUser newUser = new AppUser(request.getUsername(), request.getPassword(), request.getEmail(),
                AppUserRole.USER);

        boolean isValidEmail = emailValidator.isValid(newUser.getEmail());
        if (!isValidEmail) {
            return ResultCode.INVALID_EMAIL;
        }

        newUser.setEmail(newUser.getEmail().toLowerCase());
        boolean userExists = appUserRepository.findByEmail(newUser.getEmail()).isPresent();
        if (userExists) {
            return ResultCode.EMAIL_ALREADY_REGISTERED;
        }

        newUser.setUsername(newUser.getUsername().toLowerCase());
        boolean usernameTaken = appUserRepository.findByUsername(newUser.getUsername()).isPresent();
        if (usernameTaken) {
            return ResultCode.USERNAME_ALREADY_TAKEN;
        }

        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        appUserRepository.save(newUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =
                new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusDays(7), newUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/confirm/confirm?token=" + token;
        emailSender.send(newUser.getEmail(), emailCreatorService.buildEmailConfirmRegistrationAddress(link),
                "Bestätige deine Email-Adresse");

        return ResultCode.SUCCESSFULLY_REGISTERED_NEW_USER;
    }
}
