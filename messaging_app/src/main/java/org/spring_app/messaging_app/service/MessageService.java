package org.spring_app.messaging_app.service;

import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.message.MessageDto;
import org.spring_app.messaging_app.dto.message.MessageGetRequest;
import org.spring_app.messaging_app.dto.message.MessagePostRequest;
import org.spring_app.messaging_app.entity.Message;
import org.spring_app.messaging_app.entity.MessageReceivedUserBridge;
import org.spring_app.messaging_app.entity.User;
import org.spring_app.messaging_app.repository.MessageReceivedUserBridgeRepository;
import org.spring_app.messaging_app.repository.MessageRepository;
import org.spring_app.messaging_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public List<MessageDto> getReceivedMessages(MessageGetRequest messageGetRequest) {
        User sender = userRepository.findByNick(messageGetRequest.getSender());
        User receiver = userRepository.findByNick(messageGetRequest.getReceiver());

        List<MessageDto> messageDtoList = new ArrayList<>();
        List<MessageReceivedUserBridge> sentMessages = receivedMessageRepository.findByReceiverAndSender(sender, receiver);
        List<MessageReceivedUserBridge> receivedMessages = receivedMessageRepository.findByReceiverAndSender(receiver, sender);

        setMessageDto(messageDtoList, receivedMessages);
        setMessageDto(messageDtoList, sentMessages);

        messageDtoList.sort(Comparator.comparing    (MessageDto::getDate)); //sort them by date

        messageDtoList.forEach(m -> {
            System.out.println(
                    "sender: " + m.getSender() + " receiver: " + m.getReceiver() + " content: " + m.getContent() + " date: " + m.getDate());
        });
        return messageDtoList;
    }

    private void setMessageDto(List<MessageDto> messageDtoList, List<MessageReceivedUserBridge> sentMessages) {
        sentMessages.forEach(m -> {
            MessageDto messageDto = new MessageDto();
            messageDto.setReceiver(m.getReceiver().getNick());
            messageDto.setSender(m.getMessage().getSender().getNick());
            messageDto.setContent(m.getMessage().getContent());
            messageDto.setDate(m.getMessage().getDate());
            messageDtoList.add(messageDto);
        });
    }
}

