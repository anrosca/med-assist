package inc.evil.medassist.patient.web;

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
public class PatientComponentTest extends AbstractWebIntegrationTest {
    @Test
    public void shouldBeAbleToCreatePatients() {
        String payload = """
                {
                     "firstName": "Patrick",
                     "lastName": "Stewart",
                     "birthDate": "1994-12-15",
                     "phoneNumber": "+37369985244"
                }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/patients/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeaders().getLocation()).isNotNull();

    }

    @Test
    public void shouldBeAbleToDeletePatients() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/patients/123e4567-e89b-12d3-a456-426614174000", HttpMethod.DELETE);

        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void whenDeletingPatientWhichIsDoesNotExists_shouldReturnErrorResponse() {
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/patients/1", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        String jsonResponse = response.getBody();

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Patient with id equal to [1] could not be found!");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/patients/1");
    }
}
