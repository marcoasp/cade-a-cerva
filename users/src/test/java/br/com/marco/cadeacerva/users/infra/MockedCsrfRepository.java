package br.com.marco.cadeacerva.users.infra;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MockedCsrfRepository implements CsrfTokenRepository {

    private final CsrfToken dummyToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "test-token");

    @Override
    public CsrfToken generateToken(final HttpServletRequest request) {
        return dummyToken;
    }

    @Override
    public void saveToken(final CsrfToken token, final HttpServletRequest request, final HttpServletResponse response) {

    }

    @Override
    public CsrfToken loadToken(final HttpServletRequest request) {
        return dummyToken;
    }
}
