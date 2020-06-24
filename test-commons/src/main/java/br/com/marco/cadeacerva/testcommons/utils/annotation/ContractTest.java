package br.com.marco.cadeacerva.testcommons.utils.annotation;

import br.com.marco.cadeacerva.testcommons.utils.security.DummyCsrfSecurity;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@IntegrationTest
@Import(DummyCsrfSecurity.class)
public @interface ContractTest {
}
