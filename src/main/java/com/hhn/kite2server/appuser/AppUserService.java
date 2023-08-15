package com.hhn.kite2server.appuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
