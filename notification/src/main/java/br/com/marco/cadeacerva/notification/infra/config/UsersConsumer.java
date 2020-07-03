package br.com.marco.cadeacerva.notification.infra.config;

import br.com.marco.cadeacerva.notification.domain.UsersEventConsumer;
import br.com.marco.cadeacerva.notification.endpoints.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("users")
@Slf4j
@RequiredArgsConstructor
public class UsersConsumer implements Consumer<UserDTO> {

    private final UsersEventConsumer usersEventConsumer;

    @Override
    public void accept(final UserDTO userDTO) {
        log.info("Received user on users function: {}", userDTO.toString());
        usersEventConsumer.consume(userDTO);
    }
}
