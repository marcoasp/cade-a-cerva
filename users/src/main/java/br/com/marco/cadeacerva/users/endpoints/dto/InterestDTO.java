package br.com.marco.cadeacerva.users.endpoints.dto;

import br.com.marco.cadeacerva.users.domain.Interest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@ToString
public class InterestDTO {

    private final List<String> tags = new ArrayList<>();
    private final Double pricePerLiter;

    public InterestDTO(final Interest interest) {
        this.tags.addAll(interest.getTags());
        this.pricePerLiter = interest.getPricePerLiter();
    }

    public static InterestDTO from(final Interest interest) {
        return new InterestDTO(interest);
    }
}
