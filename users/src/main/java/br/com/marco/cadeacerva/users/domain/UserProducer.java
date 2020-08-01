package br.com.marco.cadeacerva.users.domain;

import br.com.marco.cadeacerva.users.endpoints.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserProducer {

    private final StreamBridge streamBridge;

    public void produceUserMessage(UserDTO user) {
        log.info("Producing User: {}", user.toString());
        streamBridge.send("users-out-0", MessageBuilder.withPayload(user).build());
    }
}
