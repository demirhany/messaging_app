package org.spring_app.messaging_app.repository;

import org.spring_app.messaging_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNick(String nick);
    Optional<User> findByEmail(String email);
}
