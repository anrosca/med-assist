package inc.evil.medassist.appointment;

import inc.evil.medassist.appointment.web.AppointmentController;
import inc.evil.medassist.appointment.facade.AppointmentFacade;
import inc.evil.medassist.appointment.web.AppointmentResponse;
import inc.evil.medassist.common.AbstractRestTest;
import inc.evil.medassist.common.ResponseBodyMatchers;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.patient.web.PatientResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AppointmentController.class)
public class AppointmentControllerTest extends AbstractRestTest {
    @MockBean
    private AppointmentFacade appointmentFacade;

    @Test
    public void shouldBeAbleToFindAllAppointments() throws Exception {
        List<AppointmentResponse> expectedAppointments = List.of(
                AppointmentResponse.builder()
                        .id("ba4e4567-bf9c-bad3-b45b-5266141740aa")
                        .startDate("2021-12-12T17:00")
                        .endDate("2021-12-1217:45")
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
                   "startDate": "2021-12-12T09:45",
                   "endDate": "2021-12-12T08:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field endDate with value 2021-12-12T08:45 should be after startDate, with value 2021-12-12T09:45")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }

    @Test
    public void whenAppointmentDoctorIdIsEmpty_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "patientId": "244e4567-ef9c-12d3-a45b-52661417400a",
                   "startDate": "2021-12-12T09:45",
                   "endDate": "2021-12-12T10:45",
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
    public void whenAppointmentPatientIdIsAndPatientRequestIsEmpty_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "doctorId": "123e4567-e89b-12d3-a456-426614174000",
                   "startDate": "2021-12-12T09:45",
                   "endDate": "2021-12-12T10:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'patientRequest' at least should not be null but value was 'null'")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }

    @Test
    public void whenAppointmentStartDateIsNull_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "doctorId": "123e4567-e89b-12d3-a456-426614174000",
                   "patientId": "244e4567-ef9c-12d3-a45b-52661417400a",
                   "endDate": "2021-12-12T10:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'startDate' must not be null but value was 'null'")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }

    @Test
    public void whenAppointmentEndDateIsNull_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                   "doctorId": "123e4567-e89b-12d3-a456-426614174000",
                   "patientId": "244e4567-ef9c-12d3-a45b-52661417400a",
                   "startDate": "2021-12-12T09:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment"
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/appointments").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'endDate' must not be null but value was 'null'")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/appointments")));
    }
}
