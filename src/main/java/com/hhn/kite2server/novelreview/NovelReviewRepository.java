package com.hhn.kite2server.novelreview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelReviewRepository extends JpaRepository<NovelReview, Long> {
}
