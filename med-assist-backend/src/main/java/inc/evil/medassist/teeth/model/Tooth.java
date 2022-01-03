package inc.evil.medassist.teeth.model;

import inc.evil.medassist.common.entity.AbstractEntity;
import inc.evil.medassist.patient.model.Patient;
import inc.evil.medassist.treatment.model.Treatment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teeth")
@SuperBuilder
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Tooth extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    private ToothName name;
    private boolean extracted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patient;

    @ManyToMany(mappedBy = "teeth")
    @Builder.Default
    private Set<Treatment> treatments = new HashSet<>();


}
