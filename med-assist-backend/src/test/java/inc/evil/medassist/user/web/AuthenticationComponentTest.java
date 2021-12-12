package inc.evil.medassist.user.web;

import com.jayway.jsonpath.JsonPath;
import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
public class AuthenticationComponentTest extends AbstractWebIntegrationTest {
    @Test
    public void shouldBeAbleToRegisterNewUsers() {
        String payload = """
                {
                    "firstName": "Sponge",
                    "lastName": "Bob",
                    "username": "sponge",
                    "email": "sponge.bob@bikinibottom.com",
                    "password": "11111",
                    "authorities": ["POWER_USER"]
                }
                """;

        RequestEntity<String> request = makeRequestFor("/api/v1/auth/register", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        String responseBody = response.getBody();
        assertThat(JsonPath.<String>read(responseBody, "$.firstName")).isEqualTo("Sponge");
        assertThat(JsonPath.<String>read(responseBody, "$.lastName")).isEqualTo("Bob");
        assertThat(JsonPath.<String>read(responseBody, "$.username")).isEqualTo("sponge");
        assertThat(JsonPath.<String>read(responseBody, "$.email")).isEqualTo("sponge.bob@bikinibottom.com");
        assertThat(JsonPath.<String>read(responseBody, "$.authorities[0]")).isEqualTo("ROLE_POWER_USER");
    }
}
