package inc.evil.medassist.treatmentplan.model;

import inc.evil.medassist.common.entity.AbstractEntity;
import inc.evil.medassist.patient.model.Patient;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "treatment_plans")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TreatmentPlan extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patient;

    private String planName;
}
