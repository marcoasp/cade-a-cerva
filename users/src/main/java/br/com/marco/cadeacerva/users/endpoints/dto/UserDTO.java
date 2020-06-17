package br.com.marco.cadeacerva.users.endpoints.dto;

import br.com.marco.cadeacerva.users.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserDTO {

    private final String id;
    private final String email;
    private final double[] location;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.location = user.getLocation();
    }
}
