package inc.evil.medassist.doctor.web;

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
public class DoctorComponentTest extends AbstractWebIntegrationTest {
    @Test
    public void shouldBeAbleToCreateDoctors() {
        String payload = """
                {
                     "name": "Vasile",
                     "speciality": "ORTHODONTIST"
                }
                """;
        RequestEntity<String> request = makeRequestFor("/api/v1/doctors/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeaders().getLocation()).isNotNull();

    }

    @Test
    public void whenDoctorNameIsNull_shouldReturnErrorResponse() {
        String payload = """
                {
                     "speciality": "ORTHODONTIST"
                }
                """;
        RequestEntity<String> request = makeRequestFor("/api/v1/doctors/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String jsonResponse = response.getBody();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Field 'name' should not be blank but value was 'null'");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/doctors/");
    }

    @Test
    public void whenDoctorSpecialityIsNull_shouldReturnErrorResponse() {
        String payload = """
                {
                     "name": "Jim"
                }
                """;
        RequestEntity<String> request = makeRequestFor("/api/v1/doctors/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String jsonResponse = response.getBody();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Field 'speciality' should not be blank but value was 'null'");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/doctors/");
    }

    @Test
    public void shouldBeAbleToDeleteDoctors() {
        RequestEntity<Void> request = makeRequestFor("/api/v1/doctors/123e4567-e89b-12d3-a456-426614174000", HttpMethod.DELETE);

        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void whenDeletingDoctorWhichIsDoesNotExists_shouldReturnErrorResponse() {
        RequestEntity<String> request = makeRequestFor("/api/v1/doctors/1", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        String jsonResponse = response.getBody();

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("No doctor with id: '1' was found.");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/doctors/1");
    }
}
