package org.spring_app.messaging_app.repository;

import org.spring_app.messaging_app.entity.MessageReceivedUserBridge;
import org.spring_app.messaging_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageReceivedUserBridgeRepository extends JpaRepository<MessageReceivedUserBridge, Long> {

    @Query("SELECT m FROM MessageReceivedUserBridge m WHERE m.receiver = :receiver AND m.message.sender = :sender")
    List<MessageReceivedUserBridge> findByReceiverAndSender(@Param("receiver") User receiver, @Param("sender") User sender);
}

