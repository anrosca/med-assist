package inc.evil.medassist.doctor.web;

import inc.evil.medassist.common.AbstractRestTest;
import inc.evil.medassist.common.ResponseBodyMatchers;
import inc.evil.medassist.doctor.model.Speciality;
import inc.evil.medassist.doctor.service.DoctorNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = DoctorController.class)
public class DoctorControllerTest extends AbstractRestTest {
    @MockBean
    private DoctorFacade doctorFacade;

    @Test
    public void shouldBeAbleToFindAllDoctors() throws Exception {
        List<DoctorResponse> expectedDoctors = List.of(
                DoctorResponse.builder()
                        .id("123e4567-e89b-12d3-a456-426614174000")
                        .name("Jim Morrison")
                        .speciality(Speciality.ORTHODONTIST)
                        .build()
        );
        when(doctorFacade.findAll()).thenReturn(expectedDoctors);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsListAsJson(expectedDoctors));
    }

    @Test
    public void shouldBeAbleToFindDoctorsById() throws Exception {
        DoctorResponse expectedDoctor = DoctorResponse.builder()
                        .id("123e4567-e89b-12d3-a456-426614174000")
                        .name("Jim Morrison")
                        .speciality(Speciality.ORTHODONTIST)
                        .build();
        when(doctorFacade.findById("123e4567-e89b-12d3-a456-426614174000")).thenReturn(expectedDoctor);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctors/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(expectedDoctor, DoctorResponse.class));
    }

    @Test
    public void whenDoctorIsNotFound_shouldReturnErrorResponse() throws Exception {
        doThrow(new DoctorNotFoundException("No doctor with id '1' was found.")).when(doctorFacade).findById("1");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctors/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/doctors/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("No doctor with id '1' was found.")));
    }

    @Test
    public void whenCreatingDoctorWithNullName_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                    "speciality": "ORTHODONTIST"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctors").contentType(MediaType.APPLICATION_JSON_VALUE).content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/doctors")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'name' should not be blank but value was 'null'")));
    }

    @Test
    public void whenCreatingDoctorWithNullSpeciality_shouldReturnErrorResponse() throws Exception {
        String payload = """
                {
                    "name": "Jim"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctors").contentType(MediaType.APPLICATION_JSON_VALUE).content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/doctors")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Field 'speciality' should not be blank but value was 'null'")));
    }

    @Test
    public void shouldBeAbleToDeleteDoctors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/doctors/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenDeletingDoctorWhichIsNotFound_shouldReturnErrorResponse() throws Exception {
        doThrow(new DoctorNotFoundException("No doctor with id '1' was found.")).when(doctorFacade).deleteById("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/doctors/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", equalTo("/api/v1/doctors/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("No doctor with id '1' was found.")));
    }
}
