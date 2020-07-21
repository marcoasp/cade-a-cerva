package br.com.marco.cadeacerva.notification.endpoints.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class UserDTO {

    private final String id;
    private final String email;
    private final double[] location;
    private final double area;
    private final List<InterestDTO> interests = new ArrayList<>();
}
