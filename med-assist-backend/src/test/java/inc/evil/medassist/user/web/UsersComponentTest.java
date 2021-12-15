package inc.evil.medassist.user.web;

import com.jayway.jsonpath.JsonPath;
import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
public class UsersComponentTest extends AbstractWebIntegrationTest {
    @Test
    public void shouldBeAbleToCreateUsers() {
        String payload = """
                {
                    "firstName": "Patrick",
                    "lastName": "Star",
                    "username": "patrick",
                    "email": "patrick-star@gmail.com",
                    "password": "123456",
                    "authorities": ["POWER_USER"]
                }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/users", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        String responseBody = response.getBody();
        assertThat(JsonPath.<String>read(responseBody, "$.firstName")).isEqualTo("Patrick");
        assertThat(JsonPath.<String>read(responseBody, "$.lastName")).isEqualTo("Star");
        assertThat(JsonPath.<String>read(responseBody, "$.username")).isEqualTo("patrick");
        assertThat(JsonPath.<String>read(responseBody, "$.email")).isEqualTo("patrick-star@gmail.com");
        assertThat(JsonPath.<String>read(responseBody, "$.authorities[0]")).isEqualTo("ROLE_POWER_USER");
    }

    @Test
    @Sql("/test-data/users/users.sql")
    public void shouldBeAbleToFindAllUsers() {
        UserResponse[] expectedUsers = {
                UserResponse.builder()
                        .id("620e11c0-7d59-45be-85cc-0dc146532e78")
                        .firstName("Sponge")
                        .lastName("Bob")
                        .username("square-pants-1")
                        .email("sponge-bob@gmail.com")
                        .enabled(true)
                        .authorities(Set.of("ROLE_POWER_USER"))
                        .build()
        };
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/users", HttpMethod.GET);

        ResponseEntity<UserResponse[]> response = restTemplate.exchange(request, UserResponse[].class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(expectedUsers);
    }

    @Test
    public void shouldBeAbleToGetUsersById() {
        UserResponse expectedUser = UserResponse.builder()
                .id("620e11c0-7d59-45be-85cc-0dc146532e78")
                .firstName("Sponge")
                .lastName("Bob")
                .username("square-pants-1")
                .enabled(true)
                .email("sponge-bob@gmail.com")
                .authorities(Set.of("ROLE_POWER_USER"))
                .build();
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/users/620e11c0-7d59-45be-85cc-0dc146532e78", HttpMethod.GET);

        ResponseEntity<UserResponse> response = restTemplate.exchange(request, UserResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(expectedUser);
    }

    @Test
    public void shouldBeAbleToDeleteUsersById() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/users/620e11c0-7d59-45be-85cc-0dc146532e78", HttpMethod.DELETE);

        ResponseEntity<UserResponse> response = restTemplate.exchange(request, UserResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void shouldBeAbleToUpdateUsers() {
        String payload = """
                {
                    "email": "sponge-robert@gmail.com"
                }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/users/620e11c0-7d59-45be-85cc-0dc146532e78", HttpMethod.PUT, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        String responseBody = response.getBody();
        assertThat(JsonPath.<String>read(responseBody, "$.firstName")).isEqualTo("Sponge");
        assertThat(JsonPath.<String>read(responseBody, "$.lastName")).isEqualTo("Bob");
        assertThat(JsonPath.<String>read(responseBody, "$.username")).isEqualTo("square-pants-1");
        assertThat(JsonPath.<String>read(responseBody, "$.email")).isEqualTo("sponge-robert@gmail.com");
        assertThat(JsonPath.<String>read(responseBody, "$.authorities[0]")).isEqualTo("ROLE_POWER_USER");
    }
}
