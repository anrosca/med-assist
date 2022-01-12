package inc.evil.medassist.file.web;

import com.jayway.jsonpath.JsonPath;
import inc.evil.medassist.common.AbstractWebIntegrationTest;
import inc.evil.medassist.common.component.ComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
public class FileRecordComponentTest extends AbstractWebIntegrationTest {
    @Test
    @Sql("/test-data/file-records/file-records.sql")
    public void shouldBeAbleToGetFileById() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/files/3f5f4af2-8738-43be-bed3-7362ab973aec", HttpMethod.GET);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String expectedResponse = "test";
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentDisposition()).isEqualTo(ContentDisposition.attachment().filename("test.png").build());
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    @Sql("/test-data/file-records/file-records.sql")
    public void shouldBeAbleToGetFileByPatientId() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/files?patientId=8f735e81-020f-4724-94ed-6b43d8a21582", HttpMethod.GET);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$[0].patientId")).isEqualTo("8f735e81-020f-4724-94ed-6b43d8a21582");
        assertThat(JsonPath.<String>read(jsonResponse, "$[0].id")).isEqualTo("3f5f4af2-8738-43be-bed3-7362ab973aec");
        assertThat(JsonPath.<String>read(jsonResponse, "$[0].name")).isEqualTo("test.png");
        assertThat(JsonPath.<String>read(jsonResponse, "$[0].url")).isNotBlank();
        assertThat(JsonPath.<String>read(jsonResponse, "$[0].type")).isEqualTo("image/png");
        assertThat(JsonPath.<String>read(jsonResponse, "$[0].data")).isEqualTo("dGVzdA==");
        assertThat(JsonPath.<Integer>read(jsonResponse, "$[0].size")).isEqualTo(4);
    }

    @Test
    @Sql("/test-data/file-records/file-records.sql")
    public void shouldBeAbleToDeleteFiles() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/files/3f5f4af2-8738-43be-bed3-7362ab973aec", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void whenCreatingFileAndPatientDoesNotExist_shouldReturnErrorResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = makeMultipartBody("foo.txt", "test".getBytes(StandardCharsets.UTF_8), "1");
        RequestEntity<MultiValueMap<String, Object>> request = makeAuthenticatedRequestFor("/api/v1/files", HttpMethod.POST, headers, body);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/files");
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Patient with id equal to [1] could not be found!");
    }

    @Test
    @Sql("/test-data/file-records/file-records.sql")
    public void shouldBeAbleToCreateFiles() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = makeMultipartBody("data.txt", "test".getBytes(StandardCharsets.UTF_8), "8f735e81-020f-4724-94ed-6b43d8a21582");
        RequestEntity<MultiValueMap<String, Object>> request = makeAuthenticatedRequestFor("/api/v1/files", HttpMethod.POST, headers, body);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.patientId")).isEqualTo("8f735e81-020f-4724-94ed-6b43d8a21582");
        assertThat(JsonPath.<String>read(jsonResponse, "$.id")).isNotBlank();
        assertThat(JsonPath.<String>read(jsonResponse, "$.name")).isEqualTo("data.txt");
        assertThat(JsonPath.<String>read(jsonResponse, "$.url")).isNotBlank();
        assertThat(JsonPath.<String>read(jsonResponse, "$.type")).isEqualTo("application/octet-stream");
        assertThat(JsonPath.<String>read(jsonResponse, "$.data")).isEqualTo("dGVzdA==");
        assertThat(JsonPath.<Integer>read(jsonResponse, "$.size")).isEqualTo(4);
    }

    @Test
    @Sql("/test-data/file-records/file-records.sql")
    public void whenAttemptingToCreateFile_whoseSizeExceedsTheConfiguredLimit_shouldReturnErrorResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = makeMultipartBody("data.txt", makeBigRandomFile(), "8f735e81-020f-4724-94ed-6b43d8a21582");
        RequestEntity<MultiValueMap<String, Object>> request = makeAuthenticatedRequestFor("/api/v1/files", HttpMethod.POST, headers, body);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/files");
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo("Maximum upload size exceeded");
    }

    @Test
    public void whenFileDoesNotExist_shouldReturnNotFoundResponse() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/files/non-existing-file-id", HttpMethod.GET);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        String responseBody = response.getBody();
        assertThat(JsonPath.<String>read(responseBody, "$.path")).isEqualTo("/api/v1/files/non-existing-file-id");
        assertThat(JsonPath.<String>read(responseBody, "$.messages[0]")).isEqualTo("FileRecord with id equal to [non-existing-file-id] could not be found!");
    }

    @Test
    public void whenDeletingAFileWhichDoesNotExist_shouldReturnNotFoundResponse() {
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/files/non-existing-file-id", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        String responseBody = response.getBody();
        assertThat(JsonPath.<String>read(responseBody, "$.path")).isEqualTo("/api/v1/files/non-existing-file-id");
        assertThat(JsonPath.<String>read(responseBody, "$.messages[0]")).isEqualTo("FileRecord with id equal to [non-existing-file-id] could not be found!");
    }

    @Test
    public void whenPatientDoesNotExist_shouldReturnEmptyFileList() {
        FileRecordResponse[] expectedFiles = {};
        RequestEntity<Void> request = makeAuthenticatedRequestFor("/api/v1/files?patientId=non-existing-patient-id", HttpMethod.GET);

        ResponseEntity<FileRecordResponse[]> response = restTemplate.exchange(request, FileRecordResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedFiles);
    }

    private byte[] makeBigRandomFile() {
        return "w".repeat(61_000_000).getBytes(StandardCharsets.UTF_8);
    }

    private MultiValueMap<String, Object> makeMultipartBody(String fileName, byte[] content, String patientId) {
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(fileName)
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(content, fileMap));
        body.add("patientId", patientId);
        return body;
    }
}
