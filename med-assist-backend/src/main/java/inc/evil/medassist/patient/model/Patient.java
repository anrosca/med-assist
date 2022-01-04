package inc.evil.medassist.patient.model;

import inc.evil.medassist.common.entity.AbstractEntity;
import inc.evil.medassist.teeth.model.Tooth;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Patient extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;

    @Column(insertable = false)
    private boolean isDeleted;

    @Builder.Default
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tooth> teeth = new ArrayList<>();

}
