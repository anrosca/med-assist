package inc.evil.medassist.teeth.web;

import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
class TeethComponentTest extends AbstractWebIntegrationTest {

    @Test
    @Sql("/test-data/teeth/teeth.sql")
    public void shouldBeAbleToFindToothById() {
        final ToothResponse expectedToothResponse = ToothResponse.builder()
                .id("e29c2861-bdae-4190-a1f3-62a17acee817")
                .code("UR1")
                .scientificName("upper right central incisor")
                .extracted(false)
                .patientId("123e4567-e89b-12d3-a456-426614174000")
                .number(1)
                .numericCode(21)
                .build();


        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/teeth/e29c2861-bdae-4190-a1f3-62a17acee817",
                                                                  HttpMethod.GET);

        ResponseEntity<ToothResponse> response = restTemplate.exchange(request, ToothResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedToothResponse);
    }

    @Test
    @Sql("/test-data/teeth/teeth.sql")
    public void shouldBeAbleToFindTeethByPatientId() {
        ToothResponse[] expectedTeethResponse = {
                ToothResponse.builder()
                        .id("e29c2861-bdae-4190-a1f3-62a17acee817")
                        .code("UR1")
                        .scientificName("upper right central incisor")
                        .extracted(false)
                        .patientId("123e4567-e89b-12d3-a456-426614174000")
                        .number(1)
                        .numericCode(21)
                        .build()
        };


        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/teeth?patientId=123e4567-e89b-12d3-a456-426614174000",
                                                                  HttpMethod.GET);

        ResponseEntity<ToothResponse[]> response = restTemplate.exchange(request, ToothResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedTeethResponse);
    }
}
