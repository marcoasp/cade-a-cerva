package br.com.marco.cadeacerva.testcommons.utils.security;

import br.com.marco.cadeacerva.testcommons.utils.annotation.WithJwtUser;
import com.google.common.collect.ImmutableMap;
import org.assertj.core.util.Maps;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class WithJwtUserSecurityContextFactory implements WithSecurityContextFactory<WithJwtUser> {


    @Override
    public SecurityContext createSecurityContext(final WithJwtUser withJwtUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Jwt jwt = new Jwt(
                "dummy-token",
                LocalDateTime.now().toInstant(ZoneOffset.UTC),
                LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.UTC),
                Maps.newHashMap("dummy-header", "header"),
                ImmutableMap.of("sub", withJwtUser.sub(), "email", withJwtUser.email())
        );
        Authentication auth = new JwtAuthenticationToken(jwt, AuthorityUtils.createAuthorityList("USER"));
        context.setAuthentication(auth);
        return context;
    }
}
