package br.com.marco.cadeacerva.matcher.domain;

import br.com.marco.cadeacerva.matcher.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.matcher.infra.client.dto.SaleDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InterestMatcher {

    public List<Match> match(final UserDTO user, final List<SaleDTO> content) {
        return content.stream().map(s -> Match.builder().userEmail(user.getEmail()).sale(Sale.of(s)).build())
                .collect(Collectors.toList());
    }
}
