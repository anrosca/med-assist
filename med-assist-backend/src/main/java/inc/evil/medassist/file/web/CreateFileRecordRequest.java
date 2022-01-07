package inc.evil.medassist.file.web;

import inc.evil.medassist.file.model.FileRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileRecordRequest {
    @NotBlank
    private String patientId;

    @NotNull
    private MultipartFile file;

}
