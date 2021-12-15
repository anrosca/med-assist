package inc.evil.medassist.appointment;

import inc.evil.medassist.appointment.web.AppointmentController;
import inc.evil.medassist.appointment.facade.AppointmentFacade;
import inc.evil.medassist.appointment.web.AppointmentResponse;
import inc.evil.medassist.common.AbstractRestTest;
import inc.evil.medassist.common.ResponseBodyMatchers;
import inc.evil.medassist.doctor.model.Specialty;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.patient.web.PatientResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static inc.evil.medassist.common.ResponseBodyMatchers.responseBody;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppointmentController.class)
public class AppointmentControllerTest extends AbstractRestTest {
    @MockBean
    private AppointmentFacade appointmentFacade;

    @Test
    public void shouldBeAbleToFindAllAppointments() throws Exception {
        List<AppointmentResponse> expectedAppointments = List.of(
                AppointmentResponse.builder()
                        .id("ba4e4567-bf9c-bad3-b45b-5266141740aa")
                        .appointmentDate("2021-12-12")
                        .startTime("17:00:00")
                        .endTime("17:45:00")
                        .operation("Выдача каппы")
                        .doctor(DoctorResponse.builder()
                                .id("fa4e4567-af9c-aad3-a45b-5266141740aa")
                                .specialty("O")
                                .firstName("Andrei")
                                .lastName("Usaci")
                                .build())
                        .patient(PatientResponse.builder()
                                .id("f44e4567-ef9c-12d3-a45b-52661417400a")
                                .phoneNumber("+37352014789")
                                .birthDate("1994-12-12")
                                .firstName("Nadya")
                                .lastName("Usaci")
                                .build())
                        .build()
        );
        when(appointmentFacade.findAll()).thenReturn(expectedAppointments);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/appointments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsListAsJson(expectedAppointments));
    }

    @Test
    public void whenAppointmentEndTimeIsBeforeStartTime_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "doctorId": "123e4567-e89b-12d3-a456-426614174000",
                   "patientId": "244e4567-ef9c-12d3-a45b-52661417400a",
                   "appointmentDate": "2021-12-12",
                   "startTime": "09:45",
                   "endTime": "08:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field endTime with value 08:45 should be after startTime, with value 09:45")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }

    @Test
    public void whenAppointmentDoctorIdIsEmpty_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "patientId": "244e4567-ef9c-12d3-a45b-52661417400a",
                   "appointmentDate": "2021-12-12",
                   "startTime": "09:45",
                   "endTime": "10:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'doctorId' must not be blank but value was 'null'")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }

    @Test
    public void whenAppointmentPatientIdIsEmpty_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "doctorId": "123e4567-e89b-12d3-a456-426614174000",
                   "appointmentDate": "2021-12-12",
                   "startTime": "09:45",
                   "endTime": "10:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'patientId' must not be blank but value was 'null'")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }

    @Test
    public void whenAppointmentDateIsEmpty_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "doctorId": "123e4567-e89b-12d3-a456-426614174000",
                   "patientId": "244e4567-ef9c-12d3-a45b-52661417400a",
                   "startTime": "09:45",
                   "endTime": "10:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'appointmentDate' must not be null but value was 'null'")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }

    @Test
    public void whenAppointmentStartTimeIsNull_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "doctorId": "123e4567-e89b-12d3-a456-426614174000",
                   "patientId": "244e4567-ef9c-12d3-a45b-52661417400a",
                   "appointmentDate": "2021-12-12",
                   "endTime": "10:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'startTime' must not be null but value was 'null'")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }

    @Test
    public void whenAppointmentEndTimeIsNull_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "doctorId": "123e4567-e89b-12d3-a456-426614174000",
                   "patientId": "244e4567-ef9c-12d3-a45b-52661417400a",
                   "appointmentDate": "2021-12-12",
                   "startTime": "10:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'endTime' must not be null but value was 'null'")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }


    @Test
    public void findDoctorAppointments_returnResponse() throws Exception {
        List<AppointmentResponse> expectedAppointments = List.of(
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
        );
        when(appointmentFacade.findAppointmentsByDoctorId("f23e4567-e89b-12d3-a456-426614174000")).thenReturn(expectedAppointments);

        mockMvc.perform(get("/api/v1/appointments?doctorId=f23e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsListAsJson(expectedAppointments));
    }

    @Test
    public void findDoctorAppointmentsInTimeRange_returnResponse() throws Exception {
        List<AppointmentResponse> expectedAppointments = List.of(
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
        );
        LocalDate startDate = LocalDate.of(2021, 12, 12);
        LocalDate endDate = LocalDate.of(2021, 12, 12);
        when(appointmentFacade.findAppointmentsByDoctorIdInTimeRange("f23e4567-e89b-12d3-a456-426614174000", startDate, endDate)).thenReturn(expectedAppointments);

        mockMvc.perform(get("/api/v1/appointments?doctorId=f23e4567-e89b-12d3-a456-426614174000&startDate=2021-12-12&endDate=2021-12-12"))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsListAsJson(expectedAppointments));
    }

    @Test
    public void findDoctorAppointmentsInTimeRange_whenStartDateIsInvalid_returnErrorResponse() throws Exception {
        mockMvc.perform(get("/api/v1/appointments?doctorId=f23e4567-e89b-12d3-a456-426614174000&startDate=a&endDate=2021-12-12"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.path", equalTo("/api/v1/appointments")))
                .andExpect(jsonPath("$.messages[0]", equalTo("Parameter: 'startDate' is not valid. Value 'a' could not be bound to type: 'localdate'")));
    }

    @Test
    public void findDoctorAppointmentsInTimeRange_whenEndDateIsInvalid_returnErrorResponse() throws Exception {
        mockMvc.perform(get("/api/v1/appointments?doctorId=f23e4567-e89b-12d3-a456-426614174000&startDate=2021-12-12&endDate=a"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.path", equalTo("/api/v1/appointments")))
                .andExpect(jsonPath("$.messages[0]", equalTo("Parameter: 'endDate' is not valid. Value 'a' could not be bound to type: 'localdate'")));
    }
}
