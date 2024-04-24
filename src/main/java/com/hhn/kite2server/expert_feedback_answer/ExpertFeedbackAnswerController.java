package com.hhn.kite2server.expert_feedback_answer;

import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "expertfeedbackanswer")
@AllArgsConstructor
public class ExpertFeedbackAnswerController {

    private final ExpertFeedbackAnswerService expertFeedbackAnswerService;

    @GetMapping
    public Response getAllFeedbackAnswers() {
        return expertFeedbackAnswerService.getAllFeedbackAnswers();
    }

    @PutMapping
    public Response findExpertFeedbackAnswer(@RequestBody FindExpertFeedbackAnswerRequest request) {
        return expertFeedbackAnswerService.findExpertFeedbackAnswer(request);
    }

    @PostMapping
    public Response addExpertFeedbackAnswer(@RequestBody PostExpertFeedbackAnswerRequest request) {
        return expertFeedbackAnswerService.addExpertFeedbackAnswer(request);
    }

    @DeleteMapping
    public Response deleteExpertFeedbackAnswer(@RequestBody DeleteExpertFeedbackAnswerRequest request) {
        return expertFeedbackAnswerService.deleteExpertFeedbackAnswer(request);
    }
}
