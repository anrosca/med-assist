package inc.evil.medassist.appointment;

import com.jayway.jsonpath.JsonPath;
import inc.evil.medassist.appointment.web.AppointmentResponse;
import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import inc.evil.medassist.doctor.model.Specialty;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.patient.web.PatientResponse;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ComponentTest
public class AppointmentComponentTest extends AbstractWebIntegrationTest {
    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAllAppointments() {
        AppointmentResponse[] expectedAppointments = {
                AppointmentResponse.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .startDate("2021-12-12T17:00")
                        .endDate("2021-12-12T18:00")
                        .operation("Выдача каппы")
                        .doctor(DoctorResponse.builder()
                                        .id("f23e4567-e89b-12d3-a456-426614174000")
                                        .firstName("Vasile")
                                        .lastName("Usaci")
                                        .username("vusaci")
                                        .email("vusaci@gmail.com")
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
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointmentsById() {
        AppointmentResponse expectedAppointment = AppointmentResponse.builder()
                .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                .startDate("2021-12-12T17:00")
                .endDate("2021-12-12T18:00")
                .operation("Выдача каппы")
                .doctor(DoctorResponse.builder()
                                .id("f23e4567-e89b-12d3-a456-426614174000")
                                .firstName("Vasile")
                                .lastName("Usaci")
                                .username("vusaci")
                                .email("vusaci@gmail.com")
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
                .build();
        RequestEntity<Void> request = makeAuthenticatedRequestFor(
                "/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.GET);

        ResponseEntity<AppointmentResponse> response = restTemplate.exchange(request, AppointmentResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(expectedAppointment);
    }

    @Test
    public void shouldBeAbleToCreateAppointmentsWithExistingPatient() {
        String payload = """
                {
                   "doctorId": "15297b89-045a-4daa-998f-5995fd44da3e",
                   "patientId": "123e4567-e89b-12d3-a456-426614174000",
                   "startDate": "2021-12-12T09:45",
                   "existingPatient": "true",
                   "endDate": "2021-12-12T10:45",
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
    public void shouldBeAbleToCreateAppointmentsWithNewPatient() {
        String payload = """
                {
                   "doctorId": "15297b89-045a-4daa-998f-5995fd44da3e",
                   "patientRequest": {
                       "firstName" : "Bob",
                       "lastName" : "Nikolson",
                       "phoneNumber": "37369666633",
                       "birthDate": "1996-11-15"
                       },
                   "startDate": "2021-12-12T09:45",
                   "existingPatient": "false",
                   "endDate": "2021-12-12T10:45",
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
    public void shouldBeAbleToUpdateAppointments() {
        String payload = """
                {
                   "appointmentDate": "2021-12-13",
                   "startTime": "10:00",
                   "endTime": "11:30",
                   "operation": "Cleaning",
                   "details": "Regular patient"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.PUT, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.appointmentDate")).isEqualTo("2021-12-13");
        assertThat(JsonPath.<String>read(jsonResponse, "$.startTime")).isEqualTo("10:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.endTime")).isEqualTo("11:30");
        assertThat(JsonPath.<String>read(jsonResponse, "$.operation")).isEqualTo("Cleaning");
        assertThat(JsonPath.<String>read(jsonResponse, "$.details")).isEqualTo("Regular patient");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.firstName")).isEqualTo("Vasile");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.lastName")).isEqualTo("Usaci");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.firstName")).isEqualTo("Jim");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.lastName")).isEqualTo("Morrison");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToUpdateAppointmentStartTime() {
        String payload = """
                {
                   "startTime": "10:00"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.PUT, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.appointmentDate")).isEqualTo("2021-12-12");
        assertThat(JsonPath.<String>read(jsonResponse, "$.startTime")).isEqualTo("10:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.endTime")).isEqualTo("18:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.operation")).isEqualTo("Выдача каппы");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.firstName")).isEqualTo("Vasile");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.lastName")).isEqualTo("Usaci");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.firstName")).isEqualTo("Jim");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.lastName")).isEqualTo("Morrison");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToUpdateAppointmentEndTime() {
        String payload = """
                {
                   "endTime": "19:00:00"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.PUT, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.appointmentDate")).isEqualTo("2021-12-12");
        assertThat(JsonPath.<String>read(jsonResponse, "$.startTime")).isEqualTo("17:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.endTime")).isEqualTo("19:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.operation")).isEqualTo("Выдача каппы");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.firstName")).isEqualTo("Vasile");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.lastName")).isEqualTo("Usaci");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.firstName")).isEqualTo("Jim");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.lastName")).isEqualTo("Morrison");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToAssignAppointmentsToNewDoctor() {
        String payload = """
                {
                   "appointmentDate": "2021-12-13",
                   "startTime": "10:00",
                   "endTime": "11:30",
                   "operation": "Cleaning",
                   "details": "Regular patient",
                   "doctorId": "620e11c0-7d59-45be-85cc-0dc146532e78"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.PUT, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.appointmentDate")).isEqualTo("2021-12-13");
        assertThat(JsonPath.<String>read(jsonResponse, "$.startTime")).isEqualTo("10:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.endTime")).isEqualTo("11:30");
        assertThat(JsonPath.<String>read(jsonResponse, "$.operation")).isEqualTo("Cleaning");
        assertThat(JsonPath.<String>read(jsonResponse, "$.details")).isEqualTo("Regular patient");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.firstName")).isEqualTo("Sponge");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.lastName")).isEqualTo("Bob");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.firstName")).isEqualTo("Jim");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.lastName")).isEqualTo("Morrison");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToAssignAppointmentsToNewPatient() {
        String payload = """
                {
                   "appointmentDate": "2021-12-13",
                   "startTime": "10:00",
                   "endTime": "11:30",
                   "operation": "Cleaning",
                   "details": "Regular patient",
                   "patientId": "fc4ec567-ec9c-C2d3-c45b-c26c141c40cc"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.PUT, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.appointmentDate")).isEqualTo("2021-12-13");
        assertThat(JsonPath.<String>read(jsonResponse, "$.startTime")).isEqualTo("10:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.endTime")).isEqualTo("11:30");
        assertThat(JsonPath.<String>read(jsonResponse, "$.operation")).isEqualTo("Cleaning");
        assertThat(JsonPath.<String>read(jsonResponse, "$.details")).isEqualTo("Regular patient");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.firstName")).isEqualTo("Ray");
        assertThat(JsonPath.<String>read(jsonResponse, "$.patient.lastName")).isEqualTo("Charles");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.firstName")).isEqualTo("Vasile");
        assertThat(JsonPath.<String>read(jsonResponse, "$.doctor.lastName")).isEqualTo("Usaci");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenUpdatingAppointment_andNewDoctorIdDoesNotExist_shouldReturnErrorResponse() {
        String payload = """
                {
                   "appointmentDate": "2021-12-13",
                   "startTime": "10:00",
                   "endTime": "11:30",
                   "operation": "Cleaning",
                   "details": "Regular patient",
                   "doctorId": "unknown"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.PUT, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa");
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Doctor with id equal to [unknown] could not be found!");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenUpdatingAppointment_andNewPatientIdDoesNotExist_shouldReturnErrorResponse() {
        String payload = """
                {
                   "appointmentDate": "2021-12-13",
                   "startTime": "10:00",
                   "endTime": "11:30",
                   "operation": "Cleaning",
                   "details": "Regular patient",
                   "patientId": "unknown"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.PUT, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa");
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Patient with id equal to [unknown] could not be found!");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenNewAppointmentConflictsWithAnExistingOne_shouldReturnErrorResponse() {
        String payload = """
                {
                   "doctorId": "f23e4567-e89b-12d3-a456-426614174000",
                   "patientId": "f44e4567-ef9c-12d3-a45b-52661417400a",
                   "existingPatient": "true",
                   "startDate": "2021-12-12T17:00",
                   "endDate": "2021-12-12T17:30",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/appointments/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo(
                "Doctor Vasile Usaci has already an appointment, starting from 17:00 till 18:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/");
    }

    @Test
    public void whenAppointmentWithTheGivenIdIsNotFound_shouldReturnErrorResponse() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/appointments/1", HttpMethod.GET);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String jsonResponse = response.getBody();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo(
                "Appointment with id equal to [1] could not be found!");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/1");
    }

    @Test
    public void whenDeletingAppointment_AndTheGivenIdIsNotFound_shouldReturnErrorResponse() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/appointments/1", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String jsonResponse = response.getBody();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo(
                "Appointment with id equal to [1] could not be found!");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/1");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToDeleteAppointments() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor(
                "/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointmentsByDoctorId() {
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
                                .authorities(null)
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
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/appointments?doctorId=f23e4567-e89b-12d3-a456-426614174000", HttpMethod.GET);

        ResponseEntity<AppointmentResponse[]> response = restTemplate.exchange(request, AppointmentResponse[].class);

        AssertionsForClassTypes.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        AssertionsForClassTypes.assertThat(response.getBody()).isEqualTo(expectedAppointments);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindDoctorAppointmentsInTimeRange() {
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
                                .authorities(null)
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
        RequestEntity<Void> request =
                makeAuthenticatedRequestFor("/api/v1/appointments?doctorId=f23e4567-e89b-12d3-a456-426614174000&startDate=2021-12-12&endDate=2021-12-12", HttpMethod.GET);

        ResponseEntity<AppointmentResponse[]> response = restTemplate.exchange(request, AppointmentResponse[].class);

        AssertionsForClassTypes.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        AssertionsForClassTypes.assertThat(response.getBody()).isEqualTo(expectedAppointments);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldReturnEmptyList_whenDoctorHasNoAppointments() {
        RequestEntity<Void> request =
                makeAuthenticatedRequestFor("/api/v1/appointments?doctorId=15297b89-045a-4daa-998f-5995fd44da3e", HttpMethod.GET);

        ResponseEntity<AppointmentResponse[]> response = restTemplate.exchange(request, AppointmentResponse[].class);

        AssertionsForClassTypes.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        AssertionsForClassTypes.assertThat(response.getBody()).isEmpty();
    }
}
