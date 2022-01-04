package inc.evil.medassist.treatment.model;

import inc.evil.medassist.common.entity.AbstractEntity;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.teeth.model.Tooth;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "treatments")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Treatment extends AbstractEntity {
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Doctor doctor;
    private Double price;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "treatment_tooth", joinColumns = @JoinColumn(name = "treatment_id"), inverseJoinColumns = @JoinColumn(name = "tooth_id"))
    private Set<Tooth> teeth = new HashSet<>();
}
