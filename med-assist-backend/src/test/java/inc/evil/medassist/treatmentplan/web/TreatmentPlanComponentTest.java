package inc.evil.medassist.treatmentplan.web;

import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import inc.evil.medassist.common.error.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
public class TreatmentPlanComponentTest extends AbstractWebIntegrationTest {
    @Test
    @Sql("/test-data/treatment-plans/treatment-plans.sql")
    public void shouldBeAbleToGetAllTreatmentPlans() {
        TreatmentPlanResponse[] expectedPlans = {
            TreatmentPlanResponse.builder()
                    .id("ae3e4567-e89b-e2d3-a4e6-426e14174b0a")
                    .patientId("123e4567-e89b-12d3-a456-426614174000")
                    .planName("Install braces")
                    .build(),
                TreatmentPlanResponse.builder()
                        .id("be3e4567-eb9b-e2d3-b4e6-c26e14174bba")
                        .patientId("123e4567-e89b-12d3-a456-426614174000")
                        .planName("Splinting of teeth following trauma")
                        .build()
        };
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/treatment-plans", HttpMethod.GET);

        ResponseEntity<TreatmentPlanResponse[]> response = restTemplate.exchange(request, TreatmentPlanResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedPlans);
    }

    @Test
    @Sql("/test-data/treatment-plans/treatment-plans.sql")
    public void shouldBeAbleToGetTreatmentPlanById() {
        TreatmentPlanResponse expectedResponse = TreatmentPlanResponse.builder()
                        .id("ae3e4567-e89b-e2d3-a4e6-426e14174b0a")
                        .patientId("123e4567-e89b-12d3-a456-426614174000")
                        .planName("Install braces")
                        .build();
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/treatment-plans/ae3e4567-e89b-e2d3-a4e6-426e14174b0a", HttpMethod.GET);

        ResponseEntity<TreatmentPlanResponse> response = restTemplate.exchange(request, TreatmentPlanResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    @Sql("/test-data/treatment-plans/treatment-plans.sql")
    public void whenTreatmentPlanDoesNotExist_shouldReturnErrorResponse() {
        ErrorResponse expectedResponse = ErrorResponse.builder()
                .path("/api/v1/treatment-plans/1")
                .messages(Set.of("TreatmentPlan with id equal to [1] could not be found!"))
                .build();
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/treatment-plans/1", HttpMethod.GET);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(request, ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    @Sql("/test-data/treatment-plans/treatment-plans.sql")
    public void shouldBeAbleToDeleteTreatmentPlanById() {
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/treatment-plans/ae3e4567-e89b-e2d3-a4e6-426e14174b0a", HttpMethod.DELETE);

        ResponseEntity<TreatmentPlanResponse> response = restTemplate.exchange(request, TreatmentPlanResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql("/test-data/treatment-plans/treatment-plans.sql")
    public void whenDeletingTreatmentPlanWhichDoesNotExist_shouldReturnErrorResponse() {
        ErrorResponse expectedResponse = ErrorResponse.builder()
                .path("/api/v1/treatment-plans/1")
                .messages(Set.of("TreatmentPlan with id equal to [1] could not be found!"))
                .build();
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/treatment-plans/1", HttpMethod.DELETE);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(request, ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }
}
