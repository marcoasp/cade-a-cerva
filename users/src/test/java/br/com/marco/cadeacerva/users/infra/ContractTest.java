package br.com.marco.cadeacerva.users.infra;

import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import br.com.marco.cadeacerva.users.infra.config.DummyCsrfSecurity;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

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
