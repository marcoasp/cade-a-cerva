package br.com.marco.cadeacerva.testcommons.utils.annotation;

import br.com.marco.cadeacerva.testcommons.utils.security.WithJwtUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithJwtUserSecurityContextFactory.class)
public @interface WithJwtUser {
    String sub() default "test";
    String email() default "test@email.com";
}
