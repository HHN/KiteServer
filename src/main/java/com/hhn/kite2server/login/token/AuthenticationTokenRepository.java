package com.hhn.kite2server.login.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Integer> {

    @Query(value = """
      select t from AuthenticationToken t inner join AppUser u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<AuthenticationToken> findAllValidTokenByUser(Long id);

    Optional<AuthenticationToken> findByToken(String token);
}