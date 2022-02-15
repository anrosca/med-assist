package inc.evil.medassist.treatmentplan.model;

import inc.evil.medassist.common.entity.AbstractEntity;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.teeth.model.Tooth;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "treatment_plan_items")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class TreatmentPlanItem extends AbstractEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TreatmentPlan treatmentPlan;

    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Doctor doctor;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Tooth> teeth = new HashSet<>();

    private LocalDate scheduledAt;

    private LocalDate completedAt;

    @Enumerated(EnumType.STRING)
    private TreatmentPlanItemStatus status;
}
