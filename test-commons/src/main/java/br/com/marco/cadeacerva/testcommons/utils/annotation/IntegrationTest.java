package br.com.marco.cadeacerva.testcommons.utils.annotation;


import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ActiveProfiles(IntegrationTest.PROFILE)
public @interface IntegrationTest {
    String PROFILE = "integration-tests";
}
