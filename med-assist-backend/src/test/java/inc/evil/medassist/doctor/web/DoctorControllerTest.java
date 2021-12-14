package inc.evil.medassist.doctor.web;

import inc.evil.medassist.common.AbstractRestTest;
import inc.evil.medassist.common.error.ErrorResponse;
import inc.evil.medassist.doctor.facade.DoctorFacade;
import inc.evil.medassist.user.model.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.Set;

import static inc.evil.medassist.common.ResponseBodyMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DoctorController.class)
public class DoctorControllerTest extends AbstractRestTest {
    @MockBean
    private DoctorFacade doctorFacade;

    @Test
    @WithMockUser(username = "square-pants-1", roles = Authority.Fields.POWER_USER)
    public void create_whenNoValidationErrors_creates() throws Exception {
        DoctorResponse expectedDoctor = DoctorResponse.builder()
                .id("620e11c0-7d59-45be-85cc-0dc146532e78")
                .firstName("Patrick")
                .lastName("Star")
                .username("patrick")
                .email("patrick-star@gmail.com")
                .authorities(Set.of("DOCTOR"))
                .telephoneNumber("3736932345")
                .specialty("ORTHODONTIST")
                .build();
        when(doctorFacade.create(any())).thenReturn(expectedDoctor);
        String payload = """
                {
                    "firstName": "Patrick",
                    "lastName": "Star",
                    "username": "patrick",
                    "email": "patrick-star@gmail.com",
                    "password": "123456",
                    "authorities": ["DOCTOR"],
                    "specialty": "ORTHODONTIST",
                    "telephoneNumber": "3736932345"
                }
                """;
        mockMvc.perform(post("/api/v1/doctors").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo("620e11c0-7d59-45be-85cc-0dc146532e78")))
                .andExpect(jsonPath("$.firstName", equalTo("Patrick")))
                .andExpect(jsonPath("$.lastName", equalTo("Star")))
                .andExpect(jsonPath("$.username", equalTo("patrick")))
                .andExpect(jsonPath("$.specialty", equalTo("ORTHODONTIST")))
                .andExpect(jsonPath("$.telephoneNumber", equalTo("3736932345")))
                .andExpect(jsonPath("$.authorities[0]", equalTo("DOCTOR")));
    }

    @Test
    public void create_whenHasValidationErrors_returnsErrorResponse() throws Exception {
        String payload = """
                {}
                """;
        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .path("/api/v1/doctors")
                .messages(Set.of(
                        "Field 'firstName' must not be blank but value was 'null'",
                        "Field 'email' must not be blank but value was 'null'",
                        "Field 'username' must not be blank but value was 'null'",
                        "Field 'password' must not be blank but value was 'null'",
                        "Field 'authorities' must not be empty but value was 'null'",
                        "Field 'lastName' must not be blank but value was 'null'",
                        "Field 'telephoneNumber' must not be blank but value was 'null'",
                        "Field 'specialty' must not be null but value was 'null'"
                                ))
                .build();

        mockMvc.perform(post("/api/v1/doctors").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsObjectAsJson(expectedErrorResponse, ErrorResponse.class));
    }

    @Test
    @WithMockUser(username = "square-pants-1", roles = Authority.Fields.POWER_USER)
    public void update_whenNoValidationErrors_updates() throws Exception {
        DoctorResponse expectedDoctor = DoctorResponse.builder()
                .id("620e11c0-7d59-45be-85cc-0dc146532e78")
                .firstName("Patrick")
                .lastName("Star")
                .username("patrick")
                .email("sponge-star@gmail.com")
                .authorities(Set.of("DOCTOR"))
                .telephoneNumber("3736932345")
                .specialty("ORTHODONTIST")
                .build();
        when(doctorFacade.update(any(), any())).thenReturn(expectedDoctor);
        String payload = """
                {
                    "email": "sponge-star@gmail.com"
                }
                """;
        mockMvc.perform(put("/api/v1/doctors/1").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("620e11c0-7d59-45be-85cc-0dc146532e78")))
                .andExpect(jsonPath("$.firstName", equalTo("Patrick")))
                .andExpect(jsonPath("$.lastName", equalTo("Star")))
                .andExpect(jsonPath("$.email", equalTo("sponge-star@gmail.com")))
                .andExpect(jsonPath("$.username", equalTo("patrick")))
                .andExpect(jsonPath("$.specialty", equalTo("ORTHODONTIST")))
                .andExpect(jsonPath("$.telephoneNumber", equalTo("3736932345")))
                .andExpect(jsonPath("$.authorities[0]", equalTo("DOCTOR")));
    }

    @Test
    public void findById_returnsResponse() throws Exception {
        DoctorResponse expectedDoctor = DoctorResponse.builder()
                .id("620e11c0-7d59-45be-85cc-0dc146532e78")
                .firstName("Patrick")
                .lastName("Star")
                .username("patrick")
                .email("sponge-star@gmail.com")
                .authorities(Set.of("DOCTOR"))
                .telephoneNumber("3736932345")
                .specialty("ORTHODONTIST")
                .build();
        when(doctorFacade.findById(any())).thenReturn(expectedDoctor);
        mockMvc.perform(get("/api/v1/doctors/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("620e11c0-7d59-45be-85cc-0dc146532e78")))
                .andExpect(jsonPath("$.firstName", equalTo("Patrick")))
                .andExpect(jsonPath("$.lastName", equalTo("Star")))
                .andExpect(jsonPath("$.email", equalTo("sponge-star@gmail.com")))
                .andExpect(jsonPath("$.username", equalTo("patrick")))
                .andExpect(jsonPath("$.specialty", equalTo("ORTHODONTIST")))
                .andExpect(jsonPath("$.telephoneNumber", equalTo("3736932345")))
                .andExpect(jsonPath("$.authorities[0]", equalTo("DOCTOR")));
    }

    @Test
    public void findAll_returnsResponse() throws Exception {
        DoctorResponse expectedDoctor = DoctorResponse.builder()
                .id("620e11c0-7d59-45be-85cc-0dc146532e78")
                .firstName("Patrick")
                .lastName("Star")
                .username("patrick")
                .email("sponge-star@gmail.com")
                .authorities(Set.of("DOCTOR"))
                .telephoneNumber("3736932345")
                .specialty("ORTHODONTIST")
                .build();
        when(doctorFacade.findAll()).thenReturn(Collections.singletonList(expectedDoctor));
        mockMvc.perform(get("/api/v1/doctors").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo("620e11c0-7d59-45be-85cc-0dc146532e78")))
                .andExpect(jsonPath("$[0].firstName", equalTo("Patrick")))
                .andExpect(jsonPath("$[0].lastName", equalTo("Star")))
                .andExpect(jsonPath("$[0].email", equalTo("sponge-star@gmail.com")))
                .andExpect(jsonPath("$[0].username", equalTo("patrick")))
                .andExpect(jsonPath("$[0].specialty", equalTo("ORTHODONTIST")))
                .andExpect(jsonPath("$[0].telephoneNumber", equalTo("3736932345")))
                .andExpect(jsonPath("$[0].authorities[0]", equalTo("DOCTOR")));
    }

}
