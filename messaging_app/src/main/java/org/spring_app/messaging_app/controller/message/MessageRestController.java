package org.spring_app.messaging_app.controller.message;

import lombok.RequiredArgsConstructor;
import org.spring_app.messaging_app.dto.message.MessageDto;
import org.spring_app.messaging_app.dto.message.MessageGetRequest;
import org.spring_app.messaging_app.dto.message.MessagePostRequest;
import org.spring_app.messaging_app.repository.UserRepository;
import org.spring_app.messaging_app.service.MessageService;
import org.spring_app.messaging_app.service.UserService;
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
    private final UserService userService;

    @PostMapping("/send")
    public ResponseEntity<MessagePostRequest> sendMessage(@AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestBody MessagePostRequest messagePostRequest) {
        String sender = userService.getUserByEmail(userDetails.getUsername()).getNick();
//        String sender = userRepository.findByEmail(userDetails.getUsername()).orElseThrow().getNick();
        messagePostRequest.setSender(sender);

        messageService.saveMessage(messagePostRequest);
        //todo: make error handling
        return ResponseEntity.ok(messagePostRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<List<MessageDto>> getMessages(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("receiver") String receiver) {
        String sender = userService.getUserByEmail(userDetails.getUsername()).getNick();

        MessageGetRequest messageGetRequest = MessageGetRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        List<MessageDto> messages = messageService.getReceivedMessages(messageGetRequest);
        return ResponseEntity.ok(messages);
    }
}
