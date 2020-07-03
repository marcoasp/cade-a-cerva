package br.com.marco.cadeacerva.notification.infra.config;

import br.com.marco.cadeacerva.notification.domain.UsersEventConsumer;
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
import org.springframework.test.context.junit4.SpringRunner;

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
    UsersEventConsumer consumer;

    @Test
    public void shouldConsumeUsersEvents() {
        trigger.trigger("sendUserMessage");
        verify(consumer).consume(any(UserDTO.class));
    }
}