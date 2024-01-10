package com.hhn.kite2server.ai_review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiReviewRepository extends JpaRepository<AiReview, Long> {
}
