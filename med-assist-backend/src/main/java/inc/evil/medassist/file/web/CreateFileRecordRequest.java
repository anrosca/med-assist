package inc.evil.medassist.file.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileRecordRequest {
    @NotBlank
    private String patientId;

    @NotNull
    private MultipartFile file;

}
