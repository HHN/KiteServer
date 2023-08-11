package com.hhn.kite2server.account.changepassword;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChangePasswordService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean changePassword(AppUser user, String oldPassword, String newPassword) {
        if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            appUserRepository.save(user);
            return true;
        }
        return false;
    }
}
