package com.hhn.kite2server.expert_feedback_questions;

import com.hhn.kite2server.expert_feedback_answer.ExpertFeedbackAnswer;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class ExpertFeedbackQuestion {
    @SequenceGenerator(name = "expert_feedback_question_sequence",
            sequenceName = "expert_feedback_question_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expert_feedback_question_sequence")
    private Long id;

    private Long novelId;

    private String novelName;

    private String userUuid;

    @Lob
    private String prompt;

    @Lob
    private String aiFeedback;

    @Lob
    private String dialogue;

    @Lob
    private String expertFeedbackQuestion;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "expert_feedback_answer_id", referencedColumnName = "id")
    private ExpertFeedbackAnswer expertFeedbackAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getAiFeedback() {
        return aiFeedback;
    }

    public void setAiFeedback(String aiFeedback) {
        this.aiFeedback = aiFeedback;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public String getExpertFeedbackQuestion() {
        return expertFeedbackQuestion;
    }

    public void setExpertFeedbackQuestion(String expertFeedbackQuestion) {
        this.expertFeedbackQuestion = expertFeedbackQuestion;
    }

    public ExpertFeedbackAnswer getExpertFeedbackAnswer() {
        return expertFeedbackAnswer;
    }

    public void setExpertFeedbackAnswer(ExpertFeedbackAnswer expertFeedbackAnswer) {
        this.expertFeedbackAnswer = expertFeedbackAnswer;
    }
}
