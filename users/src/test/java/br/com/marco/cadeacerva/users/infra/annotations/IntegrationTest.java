package br.com.marco.cadeacerva.users.infra.annotations;


import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ActiveProfiles(IntegrationTest.PROFILE)
public @interface IntegrationTest {
    String PROFILE = "integration-tests";
}
