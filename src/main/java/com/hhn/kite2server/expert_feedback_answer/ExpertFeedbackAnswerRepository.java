package com.hhn.kite2server.expert_feedback_answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertFeedbackAnswerRepository extends JpaRepository<ExpertFeedbackAnswer, Long> {
}
