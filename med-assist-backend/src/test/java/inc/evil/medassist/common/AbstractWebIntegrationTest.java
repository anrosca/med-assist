package inc.evil.medassist.common;

import inc.evil.medassist.user.web.AuthRequest;
import inc.evil.medassist.user.web.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

public class AbstractWebIntegrationTest extends AbstractIntegrationTest {
    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected <T> RequestEntity<T> makeRequestFor(String urlTemplate, HttpMethod httpMethod) {
        return makeRequestFor(urlTemplate, httpMethod, null);
    }

    protected <T> RequestEntity<T> makeRequestFor(String urlTemplate, HttpMethod httpMethod, T payload) {
        return RequestEntity.method(httpMethod, "http://localhost:{port}" + urlTemplate, port)
//                .header(HttpHeaders.AUTHORIZATION, BASIC_AUTHENTICATION_HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload);
    }

    protected <T> RequestEntity<T> makeAuthenticatedRequestFor(String urlTemplate, HttpMethod httpMethod) {
        return makeAuthenticatedRequestFor(urlTemplate, httpMethod, null);
    }

    protected <T> RequestEntity<T> makeAuthenticatedRequestFor(String urlTemplate, HttpMethod httpMethod, T payload) {
        AuthRequest authRequest = new AuthRequest("square-pants-1", "123456");
        ResponseEntity<UserResponse> authResponse = restTemplate.postForEntity("/api/v1/auth/login", authRequest, UserResponse.class);

        return RequestEntity.method(httpMethod, "http://localhost:{port}" + urlTemplate, port)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authResponse.getBody().getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload);
    }
}
