package org.spring_app.messaging_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring_app.messaging_app.entity.Message;
import org.spring_app.messaging_app.entity.User;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private String content;
    private Timestamp date;
    private String sender;
    private String receiver;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.date = message.getDate();
        this.sender = message.getSender().getNick();
    }
}
