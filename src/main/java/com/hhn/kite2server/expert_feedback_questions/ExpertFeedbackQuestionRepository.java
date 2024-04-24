package com.hhn.kite2server.expert_feedback_questions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertFeedbackQuestionRepository extends JpaRepository<ExpertFeedbackQuestion, Long> {

    List<ExpertFeedbackQuestion> findByUserUuid(String userUuid);
}
