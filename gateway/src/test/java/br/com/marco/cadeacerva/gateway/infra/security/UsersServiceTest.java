package br.com.marco.cadeacerva.gateway.infra.security;

import br.com.marco.cadeacerva.gateway.infra.client.UsersClient;
import br.com.marco.cadeacerva.gateway.infra.client.dto.UserDTO;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@IntegrationTest
@SpringBootTest
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "br.com.marco.cadeacerva:users:+:stubs:8090")
public class UsersServiceTest {

    @Autowired
    UsersService usersService;

    @Test
    public void shouldResolveUserForOidcUserInfo() {
        OidcUser user = mock(OidcUser.class, RETURNS_DEEP_STUBS);
        when(user.getEmail()).thenReturn("existing-user@email.com");
        usersService.resolveUser(user);
    }
}