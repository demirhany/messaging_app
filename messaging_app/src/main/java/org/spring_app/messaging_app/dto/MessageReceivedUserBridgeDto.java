package org.spring_app.messaging_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring_app.messaging_app.entity.Message;
import org.spring_app.messaging_app.entity.MessageReceivedUserBridge;
import org.spring_app.messaging_app.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageReceivedUserBridgeDto {
    private Long id;
    private User receiver;
    private Message message;

    public MessageReceivedUserBridgeDto(MessageReceivedUserBridge messageReceivedUserBridge) {
        this.id = messageReceivedUserBridge.getId();
        this.message = messageReceivedUserBridge.getMessage();
        this.receiver = messageReceivedUserBridge.getReceiver();
    }
}
