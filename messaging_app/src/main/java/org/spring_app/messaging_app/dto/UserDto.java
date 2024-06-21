package org.spring_app.messaging_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring_app.messaging_app.entity.Role;
import org.spring_app.messaging_app.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String nick;
    private String email;
    private List<String> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nick = user.getNick();
        for(Role role : user.getRoles()) {
            this.roles.add(role.getRoleName());
        }
    }
}
