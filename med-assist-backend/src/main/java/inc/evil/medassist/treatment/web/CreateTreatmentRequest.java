package inc.evil.medassist.treatment.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTreatmentRequest {
    @NotBlank
    private String description;
    @NotNull
    private Double price;
    @NotBlank
    private String doctorId;
    @NotBlank
    private String patientId;
    private List<String> teethIds = new ArrayList<>();
}
