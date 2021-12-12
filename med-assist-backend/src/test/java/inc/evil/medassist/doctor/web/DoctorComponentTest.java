package inc.evil.medassist.doctor.web;

import com.jayway.jsonpath.JsonPath;
import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
public class DoctorComponentTest extends AbstractWebIntegrationTest {
    @Test
    public void shouldBeAbleToCreateDoctors() {
        String payload = """
                {
                     "firstName" : "Bob",
                     "lastName" : "Dylan",
                     "username" : "bdylan",
                     "email" : "bdylan@email.com",
                     "password" : "123456",
                     "specialty" : "ORTHODONTIST",
                     "telephoneNumber" : "+37369666666",
                     "authorities" : ["DOCTOR"]
                }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/doctors/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Test
    public void shouldBeAbleToUpdateDoctor() {
        String payload = """
                {
                     "email" : "rocknroll@email.com"
                }
                """;
        final DoctorResponse doctorResponse = DoctorResponse.builder()
                .id("15297b89-045a-4daa-998f-5995fd44da3e")
                .firstName("Vasile")
                .lastName("Usaci")
                .username("vusaci")
                .email("rocknroll@email.com")
                .authorities(Set.of("ROLE_DOCTOR"))
                .specialty("ORTHODONTIST")
                .telephoneNumber("37369666666")
                .build();
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/doctors/15297b89-045a-4daa-998f-5995fd44da3e", HttpMethod.PUT, payload);

        ResponseEntity<DoctorResponse> response = restTemplate.exchange(request, DoctorResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(doctorResponse);
    }

    @Test
    public void shouldBeAbleToGetAllDoctors() {
        DoctorResponse[] doctorResponses = {
                DoctorResponse.builder()
                        .id("15297b89-045a-4daa-998f-5995fd44da3e")
                        .firstName("Vasile")
                        .lastName("Usaci")
                        .username("vusaci")
                        .email("vusaci@gmail.com")
                        .authorities(Set.of("ROLE_DOCTOR"))
                        .specialty("ORTHODONTIST")
                        .telephoneNumber("37369666666")
                        .build()
        };

        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/doctors", HttpMethod.GET);

        ResponseEntity<DoctorResponse[]> response = restTemplate.exchange(request, DoctorResponse[].class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(doctorResponses);
    }

    @Test
    public void shouldBeAbleToGetById() {
        final DoctorResponse doctorResponse = DoctorResponse.builder()
                .id("15297b89-045a-4daa-998f-5995fd44da3e")
                .firstName("Vasile")
                .lastName("Usaci")
                .username("vusaci")
                .email("vusaci@gmail.com")
                .authorities(Set.of("ROLE_DOCTOR"))
                .specialty("ORTHODONTIST")
                .telephoneNumber("37369666666")
                .build();


        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/doctors/15297b89-045a-4daa-998f-5995fd44da3e", HttpMethod.GET);

        ResponseEntity<DoctorResponse> response = restTemplate.exchange(request, DoctorResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(doctorResponse);
    }

    @Test
    public void shouldBeAbleToDeleteDoctors() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/doctors/15297b89-045a-4daa-998f-5995fd44da3e", HttpMethod.DELETE);

        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void whenDeletingDoctorWhichDoesNotExists_shouldReturnErrorResponse() {
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/doctors/1", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        String jsonResponse = response.getBody();

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Doctor with id equal to [1] could not be found!");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/doctors/1");
    }
}
