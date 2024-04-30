package com.hhn.kite2server.expert_feedback_questions;

import com.hhn.kite2server.expert_feedback_answer.ExpertFeedbackAnswer;
import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.response.ResultCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ExpertFeedbackQuestionService {

    private final ExpertFeedbackQuestionRepository expertFeedbackQuestionRepository;

    @Transactional
    public Response findExpertFeedbackQuestion(FindExpertFeedbackQuestionRequest request) {
        Response response = new Response();

        List<ExpertFeedbackQuestion> questions = expertFeedbackQuestionRepository.findByUserUuid(request.getUserUuid());

        if (questions == null || questions.isEmpty()) {
            ResultCode resultCode = ResultCode.NO_SUCH_EXPERT_FEEDBACK_QUESTION;
            response.setResultCode(resultCode.toInt());
            response.setResultText(resultCode.toString());
            return response;
        }
        response.setExpertFeedbackQuestions(questions);
        ResultCode resultCode = ResultCode.SUCCESSFULLY_FOUND_EXPERT_FEEDBACK_QUESTION;
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }

    public Response addExpertFeedbackQuestion(PostExpertFeedbackQuestionRequest request) {
        ExpertFeedbackQuestion expertFeedbackQuestion = new ExpertFeedbackQuestion();
        expertFeedbackQuestion.setNovelId(request.getNovelId());
        expertFeedbackQuestion.setNovelName(request.getNovelName());
        expertFeedbackQuestion.setUserUuid(request.getUserUuid());
        expertFeedbackQuestion.setPrompt(request.getPrompt());
        expertFeedbackQuestion.setAiFeedback(request.getAiFeedback());
        expertFeedbackQuestion.setDialogue(request.getDialogue());
        expertFeedbackQuestion.setExpertFeedbackQuestion(request.getExpertFeedbackQuestion());
        expertFeedbackQuestionRepository.save(expertFeedbackQuestion);
        Response response = new Response();
        ResultCode resultCode = ResultCode.SUCCESSFULLY_POSTET_EXPERT_FEEDBACK_QUESTION;
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }

    public Response deleteExpertFeedbackQuestion(DeleteExpertFeedbackQuestionRequest request) {
        Response response = new Response();

        Optional<ExpertFeedbackQuestion> question =
                expertFeedbackQuestionRepository.findById(request.getId());

        if (!question.isPresent()) {
            ResultCode resultCode = ResultCode.NO_SUCH_EXPERT_FEEDBACK_QUESTION;
            response.setResultCode(resultCode.toInt());
            response.setResultText(resultCode.toString());
            return response;
        }
        ExpertFeedbackQuestion expertFeedbackQuestion = question.get();
        expertFeedbackQuestionRepository.delete(expertFeedbackQuestion);

        ResultCode resultCode = ResultCode.SUCCESSFULLY_DELETED_EXPERT_FEEDBACK_QUESTION;
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }

    public Response getAllFeedbackQuestions() {
        Response response = new Response();
        List<ExpertFeedbackQuestion> answers = expertFeedbackQuestionRepository.findAll();
        response.setExpertFeedbackQuestions(answers);
        ResultCode resultCode = ResultCode.SUCCESSFULLY_GOT_ALL_EXPERT_FEEDBACK_QUESTIONS;
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }
}
