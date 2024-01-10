package com.hhn.kite2server.ai_review;

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
public class AiReview {
    @SequenceGenerator(name = "ai_review_sequence", sequenceName = "ai_review_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ai_review_sequence")
    private Long id;

    private Long novelId;

    private String novelName;

    private String prompt;

    private String aiFeedback;

    private String reviewText;
}
