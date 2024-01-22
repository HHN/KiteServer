package com.hhn.kite2server.reviewobserver;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewObserverRepository  extends JpaRepository<ReviewObserver, Long> {

    Optional<ReviewObserver> findByEmail(String email);
}
