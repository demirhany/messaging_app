package org.spring_app.messaging_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring_app.messaging_app.entity.Message;
import org.spring_app.messaging_app.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private String content;
    private String date;
    private String sender;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.date = message.getDate().toString();
        this.sender = message.getSender().getNick();
    }
}
