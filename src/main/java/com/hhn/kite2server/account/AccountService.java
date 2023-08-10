package com.hhn.kite2server.account;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.appuser.AppUserRole;
import com.hhn.kite2server.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

    private final AppUserService appUserService;

    public ResultCode register(RegistrationRequest request) {
        AppUser newUser = new AppUser(request.getUsername(), request.getPassword(), request.getEmail(), AppUserRole.USER);
        ResultCode code = appUserService.signUpUser(newUser);
        return code;
    }

    public ResultCode delete(long id) {
        if (!appUserService.isUserExistentById(id)) {
            return ResultCode.USER_NOT_FOUND;
        }
        ResultCode code = appUserService.deleteUserById(id);
        return code;
    }

    public boolean changePassword(AppUser user, String oldPassword, String newPassword) {
        return appUserService.changePassword(user, oldPassword, newPassword);
    }
}
