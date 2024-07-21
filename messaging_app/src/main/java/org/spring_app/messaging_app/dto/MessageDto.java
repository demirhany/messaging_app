package org.spring_app.messaging_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring_app.messaging_app.entity.Message;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private String content;
    private String date;
    private String user;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.date = message.getDate().toString();
        this.user = message.getUser().getNick();
    }
}
