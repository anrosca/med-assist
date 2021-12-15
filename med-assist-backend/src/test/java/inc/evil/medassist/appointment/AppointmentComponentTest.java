package inc.evil.medassist.appointment;

import com.jayway.jsonpath.JsonPath;
import inc.evil.medassist.appointment.web.AppointmentResponse;
import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import inc.evil.medassist.doctor.model.Specialty;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.patient.web.PatientResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ComponentTest
public class AppointmentComponentTest extends AbstractWebIntegrationTest {
    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAllAppointments() {
        AppointmentResponse[] expectedAppointments = {
                AppointmentResponse.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .appointmentDate("2021-12-12")
                        .startTime("17:00")
                        .endTime("18:00")
                        .operation("Выдача каппы")
                        .doctor(DoctorResponse.builder()
                                .id("f23e4567-e89b-12d3-a456-426614174000")
                                .firstName("Vasile")
                                .lastName("Usaci")
                                .username("vusaci")
                                .email("vusaci@gmail.com")
                                .authorities(Set.of("ROLE_DOCTOR"))
                                .specialty(Specialty.ORTHODONTIST.name())
                                .telephoneNumber("37369666666")
                                .enabled(true)
                                .build())
                        .patient(PatientResponse.builder()
                                .id("f44e4567-ef9c-12d3-a45b-52661417400a")
                                .firstName("Jim")
                                .lastName("Morrison")
                                .birthDate("1994-12-13")
                                .phoneNumber("+37369952147")
                                .build())
                        .build()
        };
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/appointments/", HttpMethod.GET);

        ResponseEntity<AppointmentResponse[]> response = restTemplate.exchange(request, AppointmentResponse[].class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(expectedAppointments);
    }

    @Test
    public void shouldBeAbleToCreateAppointments() {
        String payload = """
                {
                   "doctorId": "15297b89-045a-4daa-998f-5995fd44da3e",
                   "patientId": "123e4567-e89b-12d3-a456-426614174000",
                   "appointmentDate": "2021-12-12",
                   "startTime": "09:45",
                   "endTime": "10:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenNewAppointmentConflictsWithAnExistingOne_shouldReturnErrorResponse() {
        String payload = """
                {
                   "doctorId": "f23e4567-e89b-12d3-a456-426614174000",
                   "patientId": "f44e4567-ef9c-12d3-a45b-52661417400a",
                   "appointmentDate": "2021-12-12",
                   "startTime": "17:00",
                   "endTime": "17:30",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Doctor Vasile Usaci has already an appointment, starting from 17:00 till 18:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/");
    }

    @Test
    public void whenAppointmentWithTheGivenIdIsNotFound_shouldReturnErrorResponse() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/appointments/1", HttpMethod.GET);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String jsonResponse = response.getBody();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Appointment with id equal to [1] could not be found!");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/1");
    }

    @Test
    public void whenDeletingAppointment_AndTheGivenIdIsNotFound_shouldReturnErrorResponse() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/appointments/1", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String jsonResponse = response.getBody();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Appointment with id equal to [1] could not be found!");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/1");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToDeleteAppointments() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
}
