package br.com.marco.cadeacerva.users.utils.annotation;


import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ActiveProfiles(IntegrationTest.PROFILE)
public @interface IntegrationTest {
    String PROFILE = "integration-tests";
}
