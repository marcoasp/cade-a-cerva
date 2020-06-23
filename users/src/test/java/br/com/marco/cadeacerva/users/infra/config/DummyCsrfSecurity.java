package br.com.marco.cadeacerva.users.infra.config;

import br.com.marco.cadeacerva.users.infra.MockedCsrfRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@TestConfiguration
public class DummyCsrfSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .csrf().csrfTokenRepository(new MockedCsrfRepository())
            .and()
            .authorizeRequests(authorize -> authorize
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
