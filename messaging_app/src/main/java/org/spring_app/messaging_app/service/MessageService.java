package org.spring_app.messaging_app.service;

import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.MessagePostRequest;
import org.spring_app.messaging_app.dto.MessageReceivedUserBridgeDto;
import org.spring_app.messaging_app.dto.UserDto;
import org.spring_app.messaging_app.entity.Message;
import org.spring_app.messaging_app.entity.MessageReceivedUserBridge;
import org.spring_app.messaging_app.entity.User;
import org.spring_app.messaging_app.repository.MessageReceivedUserBridgeRepository;
import org.spring_app.messaging_app.repository.MessageRepository;
import org.spring_app.messaging_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageReceivedUserBridgeRepository receivedMessageRepository;
    private final UserRepository userRepository;

    //todo: Make error handling
    public void saveSentMessage(MessagePostRequest message) {
        Message message1 = new Message(
                null,
                message.getContent(),
                message.getSqlDate(),
                userRepository.findByNick(message.getSender()),
                null
        );
        messageRepository.save(message1);
    }

    public void saveReceivedMessage(MessagePostRequest message) {
        Message message2 = messageRepository.findByDateAndSender(message.getSqlDate(), userRepository.findByNick(message.getSender()));
        MessageReceivedUserBridge message1 = new MessageReceivedUserBridge(
                null,
                userRepository.findByNick(message.getReceiver()),
                message2
        );
        receivedMessageRepository.save(message1);
    }
}
