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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "expert_feedback_answer_id", referencedColumnName = "id")
    private ExpertFeedbackAnswer expertFeedbackAnswer;
}
