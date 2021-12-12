package inc.evil.medassist.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

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
}
