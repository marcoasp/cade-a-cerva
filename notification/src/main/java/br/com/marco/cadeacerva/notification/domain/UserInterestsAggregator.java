package br.com.marco.cadeacerva.notification.domain;

import br.com.marco.cadeacerva.notification.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.notification.infra.client.SalesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInterestsAggregator {

    private final SalesClient salesClient;

    public void aggregate(final UserDTO user) {
        
    }
}
