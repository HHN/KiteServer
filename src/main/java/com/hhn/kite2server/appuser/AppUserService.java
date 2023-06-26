package com.hhn.kite2server.appuser;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.email.EmailCreator;
import com.hhn.kite2server.email.EmailSender;
import com.hhn.kite2server.email.EmailValidator;
import com.hhn.kite2server.registration.token.ConfirmationToken;
import com.hhn.kite2server.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidator emailValidator;
    private final EmailSender emailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).get();
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).get();
    }

    public ResultCode signUpUser(AppUser appUser) {

        boolean isValidEmail = emailValidator.isValid(appUser.getEmail());
        if (!isValidEmail) {
            return ResultCode.INVALID_EMAIL;
        }

        appUser.setEmail(appUser.getEmail().toLowerCase());
        boolean userExists = isEmailAlreadyRegistered(appUser.getEmail());
        if (userExists) {
            return ResultCode.EMAIL_ALREADY_REGISTERED;
        }

        appUser.setUsername(appUser.getUsername().toLowerCase());
        boolean usernameTaken = isUsernameAlreadyTaken(appUser.getUsername());
        if (usernameTaken) {
            return ResultCode.USERNAME_ALREADY_TAKEN;
        }

        RegisterUser(appUser);
        SendConfirmationEmailToUser(appUser);

        return ResultCode.SUCCESSFULLY_REGISTERED_NEW_USER;
    }

    private void RegisterUser(AppUser appUser) {
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);
    }

    private void SendConfirmationEmailToUser(AppUser appUser){
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =
                new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusDays(7), appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/confirm/confirm?token=" + token;
        emailSender.send(appUser.getEmail(), EmailCreator.buildEmail(link));
    }

    public boolean isEmailAlreadyRegistered(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    public boolean isUsernameAlreadyTaken(String username) {
        return appUserRepository.findByUsername(username).isPresent();
    }

    public boolean isEmailAlreadyConfirmed(String email) {
        return isEmailAlreadyRegistered(email) && appUserRepository.findByEmail(email).get().getEnabled();
    }

    public int enableAppUser(Long id) {
        return appUserRepository.enableAppUser(id);
    }
}
