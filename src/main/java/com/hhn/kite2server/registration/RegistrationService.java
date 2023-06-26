package com.hhn.kite2server.registration;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.appuser.AppUserRole;
import com.hhn.kite2server.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;

    public ResultCode register(RegistrationRequest request) {
        AppUser newUser = new AppUser(request.getUsername(), request.getPassword(), request.getEmail(), AppUserRole.USER);
        ResultCode code = appUserService.signUpUser(newUser);
        return code;
    }
}
