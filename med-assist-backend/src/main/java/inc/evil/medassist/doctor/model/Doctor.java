package inc.evil.medassist.doctor.model;

import inc.evil.medassist.user.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name = "doctors")
@SuperBuilder @Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Doctor extends User {

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    private String telephoneNumber;
}
