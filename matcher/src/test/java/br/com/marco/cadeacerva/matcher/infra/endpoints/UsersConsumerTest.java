package br.com.marco.cadeacerva.matcher.infra.endpoints;

import br.com.marco.cadeacerva.matcher.domain.UserInterestsAggregator;
import br.com.marco.cadeacerva.matcher.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

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
    @Qualifier("users-in-0")
    MessageChannel inputDestination;

    @Autowired
    MessageCollector messageCollector;

    @Test
    public void shouldConsumeUsersEvents() {
        trigger.trigger("sendUserMessage");
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(inputDestination);
        verify(consumer).aggregate(any(UserDTO.class));
    }
}