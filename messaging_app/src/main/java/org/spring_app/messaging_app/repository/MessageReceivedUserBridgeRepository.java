package org.spring_app.messaging_app.repository;

import org.spring_app.messaging_app.entity.MessageReceivedUserBridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageReceivedUserBridgeRepository extends JpaRepository<MessageReceivedUserBridge, Long> {
}
