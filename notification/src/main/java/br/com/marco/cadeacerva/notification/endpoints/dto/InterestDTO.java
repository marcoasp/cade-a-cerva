package br.com.marco.cadeacerva.notification.endpoints.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class InterestDTO {

    private final List<String> tags;
    private final Double pricePerLiter;
}
