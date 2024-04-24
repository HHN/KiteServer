package com.hhn.kite2server.expert_feedback_questions;

import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "expertfeedbackquestion")
@AllArgsConstructor
public class ExpertFeedbackQuestionController {

    private final ExpertFeedbackQuestionService expertFeedbackQuestionService;

    @GetMapping
    public Response getAllFeedbackQuestions() {
        return expertFeedbackQuestionService.getAllFeedbackQuestions();
    }

    @PutMapping
    public Response findExpertFeedbackQuestion(@RequestBody FindExpertFeedbackQuestionRequest request) {
        return expertFeedbackQuestionService.findExpertFeedbackQuestion(request);
    }

    @PostMapping
    public Response addExpertFeedbackQuestion(@RequestBody PostExpertFeedbackQuestionRequest request) {
        return expertFeedbackQuestionService.addExpertFeedbackQuestion(request);
    }

    @DeleteMapping
    public Response deleteExpertFeedbackQuestion(@RequestBody DeleteExpertFeedbackQuestionRequest request) {
        return expertFeedbackQuestionService.deleteExpertFeedbackQuestion(request);
    }
}
