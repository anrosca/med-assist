package inc.evil.medassist.user.web;

import inc.evil.medassist.common.AbstractRestTest;
import inc.evil.medassist.user.facade.UserFacade;
import inc.evil.medassist.user.model.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest extends AbstractRestTest {
    @MockBean
    private UserFacade userFacade;

    @Test
    @WithMockUser(username = "square-pants-1", roles = Authority.Fields.POWER_USER)
    public void shouldBeAbleToCreateUsers() throws Exception {
        UserResponse expectedUser = UserResponse.builder()
                .id("620e11c0-7d59-45be-85cc-0dc146532e78")
                .firstName("Patrick")
                .lastName("Star")
                .username("patrick")
                .email("patrick-star@gmail.com")
                .authorities(Set.of("ROLE_POWER_USER"))
                .build();
        when(userFacade.create(any())).thenReturn(expectedUser);
        String payload = """
                {
                    "firstName": "Patrick",
                    "lastName": "Star",
                    "username": "patrick",
                    "email": "patrick-star@gmail.com",
                    "password": "123456",
                    "authorities": ["POWER_USER"]
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo("620e11c0-7d59-45be-85cc-0dc146532e78")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", equalTo("Patrick")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", equalTo("Star")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", equalTo("patrick")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0]", equalTo("ROLE_POWER_USER")));
    }
}
