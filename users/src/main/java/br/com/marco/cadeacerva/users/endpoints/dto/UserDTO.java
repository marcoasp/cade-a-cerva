package br.com.marco.cadeacerva.users.endpoints.dto;

import br.com.marco.cadeacerva.users.domain.Interest;
import br.com.marco.cadeacerva.users.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserDTO {

    private final String id;
    private final String email;
    private final double[] location;
    private final double area;
    private final List<InterestDTO> interests = new ArrayList<>();

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.location = user.getLocation();
        this.area = user.getArea();
        this.interests.addAll(user.getInterests().stream().collect(Collectors.mapping(InterestDTO::new, toList())));
    }
}
