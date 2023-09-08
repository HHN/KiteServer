package com.hhn.kite2server.account.deleteaccount;

import com.hhn.kite2server.comment.CommentService;
import com.hhn.kite2server.commentlikes.CommentLikeService;
import com.hhn.kite2server.account.resetpassword.ResetPasswordService;
import com.hhn.kite2server.account.token.ConfirmationTokenService;
import com.hhn.kite2server.appuser.AppUserRepository;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.login.token.AuthenticationTokenRepository;
import com.hhn.kite2server.money.MoneyService;
import com.hhn.kite2server.novellikes.NovelLikeRepository;
import com.hhn.kite2server.novels.VisualNovelService;
import com.hhn.kite2server.score.ScoreService;
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
    private final CommentLikeService commentLikeService;
    private final NovelLikeRepository novelLikeRepository;
    private final ScoreService scoreService;
    private final MoneyService moneyService;

    public ResultCode delete(AppUser user) {
        if (!appUserRepository.existsById(user.getId())) {
            return ResultCode.USER_NOT_FOUND;
        }
        commentLikeService.deleteLikesByUser(user);
        confirmationTokenService.deleteAllTokensOfUser(user.getId());
        resetPasswordTokenService.deleteAllTokensOfUser(user.getId());
        tokenRepository.deleteTokensFromUser(user.getId());
        novelService.deleteNovelsFromUser(user);
        commentService.deleteAllCommentsOfUser(user);
        novelLikeRepository.deleteByUser(user);
        scoreService.deleteScoreByUser(user);
        moneyService.deleteMoneyByUser(user);
        appUserRepository.deleteById(user.getId());
        return ResultCode.SUCCESSFULLY_DELETED_USER;
    }
}
