package br.com.marco.cadeacerva.notification.infra.endpoints;

import br.com.marco.cadeacerva.notification.domain.UserInterestsAggregator;
import br.com.marco.cadeacerva.notification.endpoints.dto.InterestDTO;
import br.com.marco.cadeacerva.notification.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@IntegrationTest
@SpringBootTest
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "br.com.marco.cadeacerva:users:+:stubs:8090")
public class UsersConsumerTest {

    @Autowired
    StubTrigger trigger;

    @MockBean
    UserInterestsAggregator consumer;

    @Autowired
    OutputDestination output;

    @Test
    public void shouldConsumeUsersEvents() {
        trigger.trigger("sendUserMessage");
        Message<byte[]> message = output.receive();
        verify(consumer).aggregate(any(UserDTO.class));
    }
}