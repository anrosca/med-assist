package inc.evil.medassist.teeth.web;

import inc.evil.medassist.teeth.model.Tooth;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ToothResponse {
    private String id;
    private String scientificName;
    private String code;
    private int number;
    private boolean extracted;
    private String patientId;

    public static ToothResponse from(Tooth tooth) {
        return ToothResponse.builder()
                .id(tooth.getId())
                .code(tooth.getName().name())
                .scientificName(tooth.getName().getScientificName())
                .number(tooth.getName().getNumber())
                .extracted(tooth.isExtracted())
                .patientId(tooth.getPatient().getId())
                .build();
    }
}
