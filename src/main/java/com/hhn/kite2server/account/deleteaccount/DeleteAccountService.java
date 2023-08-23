package com.hhn.kite2server.account.deleteaccount;

import com.hhn.kite2server.account.comment.CommentService;
import com.hhn.kite2server.account.resetpassword.ResetPasswordService;
import com.hhn.kite2server.account.token.ConfirmationTokenService;
import com.hhn.kite2server.appuser.AppUserRepository;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.login.token.AuthenticationTokenRepository;
import com.hhn.kite2server.novels.VisualNovelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAccountService {
    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final ResetPasswordService resetPasswordTokenService;
    private final VisualNovelService novelService;
    private final AuthenticationTokenRepository tokenRepository;
    private final CommentService commentService;

    public ResultCode delete(AppUser user) {
        if (!appUserRepository.existsById(user.getId())) {
            return ResultCode.USER_NOT_FOUND;
        }
        confirmationTokenService.deleteAllTokensOfUser(user.getId());
        resetPasswordTokenService.deleteAllTokensOfUser(user.getId());
        tokenRepository.deleteTokensFromUser(user.getId());
        novelService.deleteNovelsFromUser(user);
        appUserRepository.deleteById(user.getId());
        commentService.deleteAllCommentsOfUser(user);
        return ResultCode.SUCCESSFULLY_DELETED_USER;
    }
}
