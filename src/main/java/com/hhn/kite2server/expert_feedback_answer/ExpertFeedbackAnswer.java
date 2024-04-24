package com.hhn.kite2server.expert_feedback_answer;

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
public class ExpertFeedbackAnswer {
    @SequenceGenerator(name = "expert_feedback_answer_sequence",
            sequenceName = "expert_feedback_answer_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expert_feedback_answer_sequence")
    private Long id;

    private String expertName;

    @Lob
    private String expertFeedbackAnswer;

    private Long idOfExpertFeedbackQuestion;
}
