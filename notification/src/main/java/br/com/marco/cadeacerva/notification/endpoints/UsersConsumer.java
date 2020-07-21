package br.com.marco.cadeacerva.notification.endpoints;

import br.com.marco.cadeacerva.notification.domain.UserInterestsAggregator;
import br.com.marco.cadeacerva.notification.endpoints.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("users")
@Slf4j
@RequiredArgsConstructor
public class UsersConsumer implements Consumer<UserDTO> {

    private final UserInterestsAggregator userInterestsAggregator;

    @Override
    public void accept(final UserDTO userDTO) {
        log.info("Received user on users function: {}", userDTO.toString());
        userInterestsAggregator.aggregate(userDTO);
    }
}
