package br.com.marco.cadeacerva.users.contracts;

import br.com.marco.cadeacerva.testcommons.utils.annotation.WithJwtUser;
import br.com.marco.cadeacerva.users.application.UserApplicationService;
import br.com.marco.cadeacerva.users.domain.User;
import br.com.marco.cadeacerva.users.domain.UserProducer;
import br.com.marco.cadeacerva.users.domain.UsersRepository;
import br.com.marco.cadeacerva.testcommons.utils.annotation.ContractTest;
import br.com.marco.cadeacerva.users.endpoints.dto.UserDTO;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContractTest
@SpringBootTest
@WithJwtUser(email = "existing-user@email.com")
@AutoConfigureMessageVerifier
public class ContractsBaseClass {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private UserApplicationService userApplicationService;

    @Autowired
    private UserProducer userProducer;

    @Before
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(this.context);
        when(usersRepository.findByEmail("existing-user@email.com")).thenReturn(Optional.of(new User("existing-user@email.com")));
        when(usersRepository.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
        when(userApplicationService.updateUser(anyString(), any())).then(AdditionalAnswers.returnsArgAt(1));
    }

    public void sendUserMessageTriggered() {
        userProducer.produceUserMessage(UserDTO.from(new User("random-string", "existing-user@email.com", new double[]{10.0, 20.5}, 3.5)));
    }
}
