package br.com.marco.cadeacerva.gateway.infra.security;

import br.com.marco.cadeacerva.gateway.infra.client.UsersClient;
import br.com.marco.cadeacerva.gateway.infra.client.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomOidcService extends OidcUserService {

    private final UsersService usersService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        return usersService.resolveUser(super.loadUser(userRequest));
    }
}
