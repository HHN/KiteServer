package com.hhn.kite2server.appuser;

import com.hhn.kite2server.account.resetpassword.ResetPasswordService;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.email.EmailCreatorService;
import com.hhn.kite2server.email.EmailSender;
import com.hhn.kite2server.email.EmailValidator;
import com.hhn.kite2server.account.token.ConfirmationToken;
import com.hhn.kite2server.account.token.ConfirmationTokenService;
import com.hhn.kite2server.login.token.AuthenticationTokenRepository;
import com.hhn.kite2server.novels.VisualNovelService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (appUserRepository.findByUsername(username).isPresent()) {
            return appUserRepository.findByUsername(username).get();
        } else {
            return null;
        }
    }

    public boolean isEmailAlreadyRegistered(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    public boolean isEmailAlreadyConfirmed(String email) {
        return isEmailAlreadyRegistered(email) && appUserRepository.findByEmail(email).get().getEnabled();
    }

    public int enableAppUser(Long id) {
        return appUserRepository.enableAppUser(id);
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
