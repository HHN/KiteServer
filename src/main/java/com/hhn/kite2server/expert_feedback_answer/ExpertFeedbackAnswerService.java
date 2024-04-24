package com.hhn.kite2server.expert_feedback_answer;

import com.hhn.kite2server.expert_feedback_questions.ExpertFeedbackQuestion;
import com.hhn.kite2server.expert_feedback_questions.ExpertFeedbackQuestionRepository;
import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.response.ResultCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ExpertFeedbackAnswerService {

    private final ExpertFeedbackAnswerRepository expertFeedbackAnswerRepository;
    private final ExpertFeedbackQuestionRepository expertFeedbackQuestionRepository;

    public Response findExpertFeedbackAnswer(FindExpertFeedbackAnswerRequest request) {
        Response response = new Response();

        Optional<ExpertFeedbackAnswer> optional = expertFeedbackAnswerRepository.findById(request.getId());

        if (!optional.isPresent()) {
            ResultCode resultCode = ResultCode.NO_SUCH_EXPERT_FEEDBACK_ANSWER;
            response.setResultCode(resultCode.toInt());
            response.setResultText(resultCode.toString());
            return response;
        }

        List<ExpertFeedbackAnswer> resultList = new ArrayList<ExpertFeedbackAnswer>();
        ExpertFeedbackAnswer expertFeedbackAnswer = optional.get();
        resultList.add(expertFeedbackAnswer);
        response.setExpertFeedbackAnswers(resultList);

        ResultCode resultCode = ResultCode.SUCCESSFULLY_FOUND_EXPERT_FEEDBACK_ANSWER;
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }

    @Transactional
    public Response addExpertFeedbackAnswer(PostExpertFeedbackAnswerRequest request) {
        Response response = new Response();
        Optional<ExpertFeedbackQuestion> question =
                expertFeedbackQuestionRepository.findById(request.getIdOfExpertFeedbackQuestion());

        if (!question.isPresent()) {
            ResultCode resultCode = ResultCode.NO_SUCH_EXPERT_FEEDBACK_QUESTION;
            response.setResultCode(resultCode.toInt());
            response.setResultText(resultCode.toString());
            return response;
        }
        ExpertFeedbackQuestion expertFeedbackQuestion = question.get();
        ExpertFeedbackAnswer expertFeedbackAnswer = new ExpertFeedbackAnswer();

        expertFeedbackAnswer.setExpertName(request.getExpertName());
        expertFeedbackAnswer.setExpertFeedbackAnswer(request.getExpertFeedbackAnswer());
        expertFeedbackAnswer.setIdOfExpertFeedbackQuestion(expertFeedbackQuestion.getId());
        expertFeedbackAnswerRepository.save(expertFeedbackAnswer);

        expertFeedbackQuestion.setExpertFeedbackAnswer(expertFeedbackAnswer);
        expertFeedbackQuestionRepository.save(expertFeedbackQuestion);

        ResultCode resultCode = ResultCode.SUCCESSFULLY_POSTET_EXPERT_FEEDBACK_ANSWER;
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }

    @Transactional
    public Response deleteExpertFeedbackAnswer(DeleteExpertFeedbackAnswerRequest request) {
        Response response = new Response();

        Optional<ExpertFeedbackAnswer> answer =
                expertFeedbackAnswerRepository.findById(request.getId());

        if (!answer.isPresent()) {
            ResultCode resultCode = ResultCode.NO_SUCH_EXPERT_FEEDBACK_ANSWER;
            response.setResultCode(resultCode.toInt());
            response.setResultText(resultCode.toString());
            return response;
        }
        ExpertFeedbackAnswer expertFeedbackAnswer = answer.get();

        Optional<ExpertFeedbackQuestion> question =
                expertFeedbackQuestionRepository.findById(expertFeedbackAnswer.getIdOfExpertFeedbackQuestion());

        if (!question.isPresent()) {
            ResultCode resultCode = ResultCode.FAILURE;
            response.setResultCode(resultCode.toInt());
            response.setResultText(resultCode.toString());
            return response;
        }
        ExpertFeedbackQuestion expertFeedbackQuestion = question.get();
        expertFeedbackQuestion.setExpertFeedbackAnswer(null);
        expertFeedbackQuestionRepository.save(expertFeedbackQuestion);
        expertFeedbackAnswerRepository.delete(expertFeedbackAnswer);
        ResultCode resultCode = ResultCode.SUCCESSFULLY_DELETED_EXPERT_FEEDBACK_ANSWER;
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }

    public Response getAllFeedbackAnswers() {
        Response response = new Response();
        List<ExpertFeedbackAnswer> answers = expertFeedbackAnswerRepository.findAll();
        response.setExpertFeedbackAnswers(answers);
        ResultCode resultCode = ResultCode.SUCCESSFULLY_GOT_ALL_EXPERT_FEEDBACK_ANSWERS;
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }
}
