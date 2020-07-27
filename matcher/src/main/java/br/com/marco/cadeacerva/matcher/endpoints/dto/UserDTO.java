package br.com.marco.cadeacerva.matcher.endpoints.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class UserDTO {

    private final String id;
    private final String email;
    private final double[] location;
    private final double area;

    @Singular
    private final List<InterestDTO> interests = new ArrayList<>();
}
