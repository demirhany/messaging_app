package org.spring_app.messaging_app.controller.message;

import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.message.MessageDto;
import org.spring_app.messaging_app.dto.message.MessageGetRequest;
import org.spring_app.messaging_app.dto.message.MessagePostRequest;
import org.spring_app.messaging_app.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageRestController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<MessagePostRequest> sendMessage(@RequestBody MessagePostRequest messagePostRequest) {
        System.out.println("sender:" + messagePostRequest.getSender() + ". receiver:" + messagePostRequest.getReceiver() + ". content: " +
                messagePostRequest.getContent() + ". date: " + messagePostRequest.getSqlDate());

        messageService.saveSentMessage(messagePostRequest);
        messageService.saveReceivedMessage(messagePostRequest);
        //todo: make error handling

        return ResponseEntity.ok(messagePostRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<List<MessageDto>> getMessages(MessageGetRequest messageGetRequest) {
        List<MessageDto> messages = messageService.getReceivedMessages(messageGetRequest);
        return ResponseEntity.ok(messages);
    }
}
