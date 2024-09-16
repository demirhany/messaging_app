package org.spring_app.messaging_app.controller.message;

import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.message.MessageDto;
import org.spring_app.messaging_app.dto.message.MessageGetRequest;
import org.spring_app.messaging_app.dto.message.MessagePostRequest;
import org.spring_app.messaging_app.repository.UserRepository;
import org.spring_app.messaging_app.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageRestController {
    private final MessageService messageService;
    private final UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<MessagePostRequest> sendMessage(@AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestBody MessagePostRequest messagePostRequest) {
        String sender = userRepository.findByEmail(userDetails.getUsername()).orElseThrow().getNick();
        messagePostRequest.setSender(sender);

        messageService.saveMessage(messagePostRequest);
        //todo: make error handling
        return ResponseEntity.ok(messagePostRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<List<MessageDto>> getMessages(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("receiver") String receiver) {
        String sender = userRepository.findByEmail(userDetails.getUsername()).orElseThrow().getNick();

        MessageGetRequest messageGetRequest = MessageGetRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        List<MessageDto> messages = messageService.getReceivedMessages(messageGetRequest);
        return ResponseEntity.ok(messages);
    }
}
