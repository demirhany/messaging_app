package org.spring_app.messaging_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring_app.messaging_app.entity.Chat;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatDto {
    private Long id;
    private String chatName;

    public ChatDto(Chat chat) {
        this.id = chat.getId();
        this.chatName = chat.getChatName();
    }
}
