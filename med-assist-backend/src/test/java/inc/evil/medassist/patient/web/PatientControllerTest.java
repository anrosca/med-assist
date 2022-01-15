package inc.evil.medassist.patient.web;

import inc.evil.medassist.common.AbstractRestTest;
import inc.evil.medassist.common.ResponseBodyMatchers;
import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.patient.facade.PatientFacade;
import inc.evil.medassist.user.model.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTest extends AbstractRestTest {
    @MockBean
    private PatientFacade doctorFacade;

    @Test
    public void shouldBeAbleToFindAllPatients() throws Exception {
        List<PatientResponse> expectedPatients = List.of(
                PatientResponse.builder()
                        .id("123e4567-e89b-12d3-a456-426614174000")
                        .firstName("Jim")
                        .lastName("Morrison")
                        .birthDate("199412-15")
                        .phoneNumber("+37369985214")
                        .build()
        );
        when(doctorFacade.findAll()).thenReturn(expectedPatients);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsListAsJson(expectedPatients));
    }

    @Test
    public void shouldBeAbleToFindPatientsById() throws Exception {
        PatientResponse expectedPatient = PatientResponse.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .firstName("Jim")
                .lastName("Morrison")
                .birthDate("1994-12-12")
                .phoneNumber("+37369985214")
                .build();
        when(doctorFacade.findById("123e4567-e89b-12d3-a456-426614174000")).thenReturn(expectedPatient);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(expectedPatient, PatientResponse.class));
    }

    @Test
    public void whenPatientIsNotFound_shouldReturnErrorResponse() throws Exception {
        doThrow(new NotFoundException(PatientResponse.class, "id", "1")).when(doctorFacade).findById("1");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/patients/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("PatientResponse with id equal to [1] could not be found!")));
    }

    @Test
    @WithMockUser(username = "square-pants-1", roles = Authority.Fields.POWER_USER)
    public void whenCreatingPatientWithNullFirstName_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                     "lastName": "Stewart",
                     "birthDate": "1994-12-15",
                     "source": "Facebook",
                     "phoneNumber": "+37369985244"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients").contentType(MediaType.APPLICATION_JSON_VALUE).content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/patients")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'firstName' must not be blank but value was 'null'")));
    }

    @Test
    @WithMockUser(username = "square-pants-1", roles = Authority.Fields.POWER_USER)
    public void whenCreatingPatientWithNullLastName_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                      "firstName": "Patrick",
                      "birthDate": "1994-12-15",
                      "phoneNumber": "+37369985244"
                 }
                 """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients").contentType(MediaType.APPLICATION_JSON_VALUE).content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/patients")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'lastName' must not be blank but value was 'null'")));
    }

    @Test
    @WithMockUser(username = "square-pants-1", roles = Authority.Fields.POWER_USER)
    public void whenCreatingPatientWithNullBirthDate_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                      "firstName": "Patrick",
                      "lastName": "Stewart",
                      "phoneNumber": "+37369985244",
                      "source": "Facebook"
                 }
                 """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients").contentType(MediaType.APPLICATION_JSON_VALUE).content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/patients")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'birthDate' must not be null but value was 'null'")));
    }

    @Test
    @WithMockUser(username = "square-pants-1", roles = Authority.Fields.POWER_USER)
    public void shouldBeAbleToDeletePatients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patients/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "square-pants-1", roles = Authority.Fields.POWER_USER)
    public void whenDeletingPatientWhichIsNotFound_shouldReturnErrorResponse() throws Exception {
        doThrow(new NotFoundException(PatientResponse.class, "id", "1")).when(doctorFacade).deleteById("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patients/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/patients/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("PatientResponse with id equal to [1] could not be found!")));
    }
}
