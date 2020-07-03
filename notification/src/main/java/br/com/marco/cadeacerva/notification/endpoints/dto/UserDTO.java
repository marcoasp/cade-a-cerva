package br.com.marco.cadeacerva.notification.endpoints.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UserDTO {

    private final String id;
    private final String email;
    private final double[] location;
    private final double area;

    public UserDTO() {
        this(null, null, null, 0.0);
    }
}
