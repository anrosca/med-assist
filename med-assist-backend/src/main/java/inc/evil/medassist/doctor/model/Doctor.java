package inc.evil.medassist.doctor.model;

import inc.evil.medassist.common.entity.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "doctors")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Doctor extends AbstractEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private Speciality speciality;
}
