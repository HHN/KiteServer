package com.hhn.kite2server.money;

import com.hhn.kite2server.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MoneyRepository  extends JpaRepository<Money, Long> {

    Optional<Money> findByUser(AppUser user);

    Optional<Money> findById(Long id);

    @Transactional
    Optional<Money> deleteByUser(AppUser user);
}
