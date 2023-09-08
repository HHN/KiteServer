package com.hhn.kite2server.score;

import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.novelreadwrite.FindNovelRequest;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public Response getScore(AppUser user, GetScoreRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_GET_SCORE;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }

        Optional<Score> optionalScore = scoreRepository.findByUser((user));

        if (optionalScore.isPresent()) {
            response.setScore(optionalScore.get().getValue());
            ResultCode code = ResultCode.SUCCESSFULLY_GOT_SCORE;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        } else {
            ResultCode code = ResultCode.FAILED_TO_GET_SCORE;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }
        return response;
    }

    public Response addToScore(AppUser user, AddScoreRequest request) {
        Response response = new Response();

        if (user == null || request == null) {
            ResultCode code = ResultCode.FAILED_TO_UPDATE_SCORE;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }

        Optional<Score> optionalScore = scoreRepository.findByUser((user));

        if (optionalScore.isPresent()) {
            Score score = optionalScore.get();
            score.setValue(score.getValue() + request.getValue());
            scoreRepository.save(score);
            response.setScore(score.getValue());
            ResultCode code = ResultCode.SUCCESSFULLY_UPDATED_SCORE;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());

        } else {
            ResultCode code = ResultCode.FAILED_TO_UPDATE_SCORE;
            response.setResultCode(code.toInt());
            response.setResultText(code.toString());
        }
        return response;
    }

    public void deleteScoreByUser(AppUser appUser) {
        if (appUser == null) {
            return;
        }
        scoreRepository.deleteByUser(appUser);
    }

    public void saveScore(Score score) {
        if (score == null) {
            return;
        }
        scoreRepository.save(score);
    }
}
