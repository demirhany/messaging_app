package org.spring_app.messaging_app.repository;

import org.spring_app.messaging_app.entity.Message;
import org.spring_app.messaging_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByDateAndSender(Timestamp date, User sender);
}
