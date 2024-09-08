package org.spring_app.messaging_app.controller.message;

import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.MessagePostRequest;
import org.spring_app.messaging_app.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
