package br.com.marco.cadeacerva.gateway.infra.security;

import br.com.marco.cadeacerva.gateway.infra.client.UsersClient;
import br.com.marco.cadeacerva.gateway.infra.client.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersService {
    public static final String BEARER_STRING = "Bearer %s";
    private final UsersClient usersClient;

    public OidcUser resolveUser(final OidcUser oidcUser) {
        String tokenHeader = String.format(BEARER_STRING, oidcUser.getIdToken().getTokenValue());
        usersClient.saveUser(tokenHeader, oidcUser.getEmail(), new UserDTO(oidcUser.getEmail()));
        return oidcUser;
    }
}
