package inc.evil.medassist.user.web;

import inc.evil.medassist.common.AbstractRestTest;
import inc.evil.medassist.common.ResponseBodyMatchers;
import inc.evil.medassist.common.error.ErrorResponse;
import inc.evil.medassist.user.model.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AuthenticationController.class)
public class AuthenticationControllerTest extends AbstractRestTest {
    @MockBean
    private UserFacade userFacade;

    @Test
    public void shouldBeAbleToLoginUsers() throws Exception {
        UserResponse createdUser = UserResponse.builder()
                .firstName("Sponge")
                .lastName("Bob")
                .email("sponge.bob@bikinibottom.com")
                .accessToken("jwt-token")
                .id("123e4567-e89b-12d3-a456-426614174000")
                .authorities(Set.of(Authority.Fields.POWER_USER))
                .username("sponge")
                .build();
        when(userFacade.authenticate(any())).thenReturn(createdUser);
        String payload = """
                {
                    "username": "sponge",
                    "password": "11111"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", equalTo("Sponge")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", equalTo("Bob")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", equalTo("sponge")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", equalTo("sponge.bob@bikinibottom.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0]", equalTo("POWER_USER")))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.AUTHORIZATION, startsWith("Bearer ")));
    }

    @Test
    public void shouldFailAuthentication_whenCredentialsAreBad() throws Exception {
        when(userFacade.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));
        String payload = """
                {
                    "username": "sponge",
                    "password": "invalid-password"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0]", equalTo("Bad credentials")));
    }

    @Test
    public void shouldBeAbleToRegisterNewUsers() throws Exception {
        UserResponse createdUser = UserResponse.builder()
                .firstName("Sponge")
                .lastName("Bob")
                .email("sponge.bob@bikinibottom.com")
                .accessToken("jwt-token")
                .id("123e4567-e89b-12d3-a456-426614174000")
                .authorities(Set.of(Authority.Fields.POWER_USER))
                .username("sponge")
                .build();
        when(userFacade.create(any())).thenReturn(createdUser);
        String payload = """
                {
                    "firstName": "Sponge",
                    "lastName": "Bob",
                    "username": "sponge",
                    "email": "sponge.bob@bikinibottom.com",
                    "password": "11111",
                    "authorities": ["POWER_USER"]
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", equalTo("Sponge")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", equalTo("Bob")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", equalTo("sponge")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", equalTo("sponge.bob@bikinibottom.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0]", equalTo("POWER_USER")));
    }

    @Test
    public void shouldReturnErrorResponse_whenThereAreValidationErrors() throws Exception {
        String payload = """
                {}
                """;
        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .path("/api/v1/auth/register")
                .messages(Set.of(
                        "Field 'firstName' must not be blank but value was 'null'",
                        "Field 'email' must not be blank but value was 'null'",
                        "Field 'username' must not be blank but value was 'null'",
                        "Field 'password' must not be blank but value was 'null'",
                        "Field 'authorities' must not be empty but value was 'null'",
                        "Field 'lastName' must not be blank but value was 'null'"
                ))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(expectedErrorResponse, ErrorResponse.class));
    }
}
