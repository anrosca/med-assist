package inc.evil.medassist.patient.model;

import inc.evil.medassist.common.entity.AbstractEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@SuperBuilder @Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Patient extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;
}
