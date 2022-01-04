package inc.evil.medassist.treatment.web;

import inc.evil.medassist.teeth.web.ToothResponse;
import inc.evil.medassist.doctor.web.DoctorResponse;
import inc.evil.medassist.patient.web.PatientResponse;
import inc.evil.medassist.treatment.model.Treatment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TreatmentResponse {
    private String id;
    private String description;
    private double price;
    private DoctorResponse doctor;
    private PatientResponse patient;
    private List<ToothResponse> teeth = new ArrayList<>();
    private String createdAt;

    public static TreatmentResponse from(Treatment treatment) {
        return TreatmentResponse.builder()
                .id(treatment.getId())
                .description(treatment.getDescription())
                .doctor(DoctorResponse.simpleForm(treatment.getDoctor()))
                .patient(PatientResponse.from(treatment.getPatient()))
                .teeth(treatment.getTeeth().stream().map(ToothResponse::from).collect(Collectors.toList()))
                .price(treatment.getPrice())
                .createdAt(treatment.getCreatedAt().toString())
                .build();
    }
}
