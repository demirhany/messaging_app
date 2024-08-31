package org.spring_app.messaging_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessagePostRequest {
    String sender;
    String receiver;
    String content;
    String date;

    public Timestamp getSqlDate() {
        return Timestamp.valueOf(this.date.replace("T", " ").substring(0, 19));
    }
}
