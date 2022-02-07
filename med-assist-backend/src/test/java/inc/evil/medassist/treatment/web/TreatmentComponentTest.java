package inc.evil.medassist.treatment.web;

import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import inc.evil.medassist.common.error.ErrorResponse;
import inc.evil.medassist.doctor.model.Specialty;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.patient.web.PatientResponse;
import inc.evil.medassist.teeth.web.ToothResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
class TreatmentComponentTest extends AbstractWebIntegrationTest {
    @Test
    @Sql("/test-data/treatments/treatments.sql")
    public void shouldBeAbleToFindAllTreatments() {
        TreatmentResponse[] expectedTreatmentResponses = {
                TreatmentResponse.builder()
                        .id("ff01f8ab-2ce9-46db-87be-a00142830a05")
                        .doctor(DoctorResponse.builder()
                                        .id("15297b89-045a-4daa-998f-5995fd44da3e")
                                        .firstName("Vasile")
                                        .lastName("Usaci")
                                        .username("vusaci")
                                        .email("vusaci@gmail.com")
                                        .specialty(Specialty.ORTHODONTIST.name())
                                        .telephoneNumber("37369666666")
                                        .enabled(true)
                                        .build())
                        .patient(PatientResponse.builder()
                                         .id("123e4567-e89b-12d3-a456-426614174000")
                                         .firstName("Jim")
                                         .lastName("Morrison")
                                         .phoneNumber("+37369952147")
                                         .birthDate("1994-12-13")
                                         .build())
                        .teeth(List.of(ToothResponse.builder()
                                               .id("e29c2861-bdae-4190-a1f3-62a17acee817")
                                               .code("LR8")
                                               .scientificName("lower right third molar")
                                               .extracted(false)
                                               .patientId("123e4567-e89b-12d3-a456-426614174000")
                                               .number(8)
                                               .numericCode(38)
                                               .build()))
                        .price(2000.00)
                        .description("Veneers")
                        .createdAt("2022-01-02T23:04:30.908247")
                        .build()
        };


        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/treatments/", HttpMethod.GET);

        ResponseEntity<TreatmentResponse[]> response = restTemplate.exchange(request, TreatmentResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedTreatmentResponses);
    }

    @Test
    @Sql("/test-data/treatments/treatments.sql")
    public void shouldBeAbleToFindTreatmentById() {
        final TreatmentResponse expectedTreatmentResponse = TreatmentResponse.builder()
                .id("ff01f8ab-2ce9-46db-87be-a00142830a05")
                .doctor(DoctorResponse.builder()
                                .id("15297b89-045a-4daa-998f-5995fd44da3e")
                                .firstName("Vasile")
                                .lastName("Usaci")
                                .username("vusaci")
                                .email("vusaci@gmail.com")
                                .specialty(Specialty.ORTHODONTIST.name())
                                .telephoneNumber("37369666666")
                                .enabled(true)
                                .build())
                .patient(PatientResponse.builder()
                                 .id("123e4567-e89b-12d3-a456-426614174000")
                                 .firstName("Jim")
                                 .lastName("Morrison")
                                 .phoneNumber("+37369952147")
                                 .birthDate("1994-12-13")
                                 .build())
                .teeth(List.of(ToothResponse.builder()
                                       .id("e29c2861-bdae-4190-a1f3-62a17acee817")
                                       .code("LR8")
                                       .scientificName("lower right third molar")
                                       .extracted(false)
                                       .patientId("123e4567-e89b-12d3-a456-426614174000")
                                       .number(8)
                                       .numericCode(38)
                                       .build()))
                .price(2000.00)
                .description("Veneers")
                .createdAt("2022-01-02T23:04:30.908247")
                .build();


        RequestEntity<Void> request = makeAuthenticatedRequestFor(
                "/api/v1/treatments/ff01f8ab-2ce9-46db-87be-a00142830a05", HttpMethod.GET);

        ResponseEntity<TreatmentResponse> response = restTemplate.exchange(request, TreatmentResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedTreatmentResponse);
    }

    @Test
    @Sql("/test-data/treatments/treatments.sql")
    public void shouldBeAbleToFindTreatmentsByDoctorId() {
        TreatmentResponse[] expectedTreatmentResponses = {
                TreatmentResponse.builder()
                        .id("ff01f8ab-2ce9-46db-87be-a00142830a05")
                        .doctor(DoctorResponse.builder()
                                        .id("15297b89-045a-4daa-998f-5995fd44da3e")
                                        .firstName("Vasile")
                                        .lastName("Usaci")
                                        .username("vusaci")
                                        .email("vusaci@gmail.com")
                                        .specialty(Specialty.ORTHODONTIST.name())
                                        .telephoneNumber("37369666666")
                                        .enabled(true)
                                        .build())
                        .patient(PatientResponse.builder()
                                         .id("123e4567-e89b-12d3-a456-426614174000")
                                         .firstName("Jim")
                                         .lastName("Morrison")
                                         .phoneNumber("+37369952147")
                                         .birthDate("1994-12-13")
                                         .build())
                        .teeth(List.of(ToothResponse.builder()
                                               .id("e29c2861-bdae-4190-a1f3-62a17acee817")
                                               .code("LR8")
                                               .scientificName("lower right third molar")
                                               .extracted(false)
                                               .patientId("123e4567-e89b-12d3-a456-426614174000")
                                               .number(8)
                                               .numericCode(38)
                                               .build()))
                        .price(2000.00)
                        .description("Veneers")
                        .createdAt("2022-01-02T23:04:30.908247")
                        .build()
        };


        RequestEntity<Void> request = makeAuthenticatedRequestFor(
                "/api/v1/treatments?doctorId=15297b89-045a-4daa-998f-5995fd44da3e", HttpMethod.GET);

        ResponseEntity<TreatmentResponse[]> response = restTemplate.exchange(request, TreatmentResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedTreatmentResponses);
    }

    @Test
    @Sql("/test-data/treatments/treatments.sql")
    public void shouldBeAbleToFindTreatmentsByPatientId() {
        TreatmentResponse[] expectedTreatmentResponses = {
                TreatmentResponse.builder()
                        .id("ff01f8ab-2ce9-46db-87be-a00142830a05")
                        .doctor(DoctorResponse.builder()
                                        .id("15297b89-045a-4daa-998f-5995fd44da3e")
                                        .firstName("Vasile")
                                        .lastName("Usaci")
                                        .username("vusaci")
                                        .email("vusaci@gmail.com")
                                        .specialty(Specialty.ORTHODONTIST.name())
                                        .telephoneNumber("37369666666")
                                        .enabled(true)
                                        .build())
                        .patient(PatientResponse.builder()
                                         .id("123e4567-e89b-12d3-a456-426614174000")
                                         .firstName("Jim")
                                         .lastName("Morrison")
                                         .phoneNumber("+37369952147")
                                         .birthDate("1994-12-13")
                                         .build())
                        .teeth(List.of(ToothResponse.builder()
                                               .id("e29c2861-bdae-4190-a1f3-62a17acee817")
                                               .code("LR8")
                                               .scientificName("lower right third molar")
                                               .extracted(false)
                                               .patientId("123e4567-e89b-12d3-a456-426614174000")
                                               .number(8)
                                               .numericCode(38)
                                               .build()))
                        .price(2000.00)
                        .description("Veneers")
                        .createdAt("2022-01-02T23:04:30.908247")
                        .build()
        };


        RequestEntity<Void> request = makeAuthenticatedRequestFor(
                "/api/v1/treatments?patientId=123e4567-e89b-12d3-a456-426614174000", HttpMethod.GET);

        ResponseEntity<TreatmentResponse[]> response = restTemplate.exchange(request, TreatmentResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedTreatmentResponses);
    }

    @Test
    @Sql("/test-data/treatments/treatments.sql")
    public void shouldBeAbleToFindTreatmentsByToothId() {
        TreatmentResponse[] expectedTreatmentResponses = {
                TreatmentResponse.builder()
                        .id("ff01f8ab-2ce9-46db-87be-a00142830a05")
                        .doctor(DoctorResponse.builder()
                                        .id("15297b89-045a-4daa-998f-5995fd44da3e")
                                        .firstName("Vasile")
                                        .lastName("Usaci")
                                        .username("vusaci")
                                        .email("vusaci@gmail.com")
                                        .specialty(Specialty.ORTHODONTIST.name())
                                        .telephoneNumber("37369666666")
                                        .enabled(true)
                                        .build())
                        .patient(PatientResponse.builder()
                                         .id("123e4567-e89b-12d3-a456-426614174000")
                                         .firstName("Jim")
                                         .lastName("Morrison")
                                         .phoneNumber("+37369952147")
                                         .birthDate("1994-12-13")
                                         .build())
                        .teeth(List.of(ToothResponse.builder()
                                               .id("e29c2861-bdae-4190-a1f3-62a17acee817")
                                               .code("LR8")
                                               .scientificName("lower right third molar")
                                               .extracted(false)
                                               .patientId("123e4567-e89b-12d3-a456-426614174000")
                                               .number(8)
                                               .numericCode(38)
                                               .build()))
                        .price(2000.00)
                        .description("Veneers")
                        .createdAt("2022-01-02T23:04:30.908247")
                        .build()
        };


        RequestEntity<Void> request = makeAuthenticatedRequestFor(
                "/api/v1/treatments?toothId=e29c2861-bdae-4190-a1f3-62a17acee817", HttpMethod.GET);

        ResponseEntity<TreatmentResponse[]> response = restTemplate.exchange(request, TreatmentResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedTreatmentResponses);
    }

    @Test
    public void shouldBeAbleToCreateTreatments() {
        String payload = """
                {
                      "description": "Extraction",
                      "price": 750.00,
                      "doctorId": "15297b89-045a-4daa-998f-5995fd44da3e",
                      "patientId": "123e4567-e89b-12d3-a456-426614174000",
                      "treatedTeeth": [
                        {
                            "toothId": "e29c2861-bdae-4190-a1f3-62a17acee817",
                            "isExtracted": true
                        }
                      ]
                }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/treatments/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Test
    public void whenCreatingTreatmentWithNoToothIds_shouldReturnErrorResponse() {
        ErrorResponse expectedResponse = ErrorResponse.builder()
                .path("/api/v1/treatments/")
                .messages(Set.of(
                        "Field 'treatedTeeth[0].isExtracted' must not be null but value was 'null'",
                        "Field 'treatedTeeth[0].toothId' must not be blank but value was ''"
                ))
                .build();
        String payload = """
                {
                      "description": "Extraction",
                      "price": 750.00,
                      "doctorId": "15297b89-045a-4daa-998f-5995fd44da3e",
                      "patientId": "123e4567-e89b-12d3-a456-426614174000",
                      "treatedTeeth": [
                        {
                            "toothId": "",
                            "isExtracted": null
                        }
                      ]
                }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/treatments/", HttpMethod.POST, payload);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(request, ErrorResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldBeAbleToCreateTreatments_withNoTeeth() {
        String payload = """
                {
                      "description": "Extraction",
                      "price": 750.00,
                      "doctorId": "15297b89-045a-4daa-998f-5995fd44da3e",
                      "patientId": "123e4567-e89b-12d3-a456-426614174000",
                      "treatedTeeth": []
                }
                """;
        RequestEntity<String> request = makeAuthenticatedRequestFor("/api/v1/treatments/", HttpMethod.POST, payload);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(request, ErrorResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Test
    @Sql("/test-data/treatments/treatments.sql")
    public void shouldBeAbleToDeleteTreatments() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor(
                "/api/v1/treatments/ff01f8ab-2ce9-46db-87be-a00142830a05", HttpMethod.DELETE);

        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }
}
