package br.com.maro.cadeacerva.users.endpoints.dto;

import br.com.maro.cadeacerva.users.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserDTO {

    public final String id;
    public final String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }
}
