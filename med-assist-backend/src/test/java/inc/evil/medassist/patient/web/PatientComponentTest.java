package inc.evil.medassist.patient.web;

import com.jayway.jsonpath.JsonPath;
import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
public class PatientComponentTest extends AbstractWebIntegrationTest {


    @Test
    @Sql("/test-data/patients/patients.sql")
    public void shouldBeAbleToCountPatientsPerAgeCategory() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/patients/count/per-age-category", HttpMethod.GET);

        ResponseEntity<Map<String, Long>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        AssertionsForClassTypes.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        AssertionsForClassTypes.assertThat(response.getBody()).isEqualTo(Map.of("25-64", 1L));
    }

    @Test
    @Sql("/test-data/patients/patients.sql")
    public void shouldBeAbleToCountPatientsPerMonth() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/patients/count/per-month", HttpMethod.GET);

        ResponseEntity<Map<String, Long>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

        AssertionsForClassTypes.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        AssertionsForClassTypes.assertThat(response.getBody()).isEqualTo(Map.of("Dec-2021", 1L));
    }

    @Test
    @Sql("/test-data/patients/patients.sql")
    public void shouldBeAbleToFindAllPatients() {
        PatientResponse[] expectedPatients = {
            PatientResponse.builder()
                    .id("123e4567-e89b-12d3-a456-426614174000")
                    .firstName("Jim")
                    .lastName("Morrison")
                    .phoneNumber("+37369952147")
                    .birthDate("1994-12-13")
                    .build()
        };
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/patients/", HttpMethod.GET);

        ResponseEntity<PatientResponse[]> response = restTemplate.exchange(request, PatientResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedPatients);
    }

    @Test
    @Sql("/test-data/patients/patients.sql")
    public void shouldBeAbleToFindPatientsById() {
        PatientResponse expectedPatient = PatientResponse.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .firstName("Jim")
                .lastName("Morrison")
                .phoneNumber("+37369952147")
                .birthDate("1994-12-13")
                .build();
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/patients/123e4567-e89b-12d3-a456-426614174000", HttpMethod.GET);

        ResponseEntity<PatientResponse> response = restTemplate.exchange(request, PatientResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedPatient);
    }

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
