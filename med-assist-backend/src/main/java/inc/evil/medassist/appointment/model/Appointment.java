package inc.evil.medassist.appointment.model;

import inc.evil.medassist.common.entity.AbstractEntity;
import inc.evil.medassist.doctor.model.Doctor;
import inc.evil.medassist.patient.model.Patient;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patient;

    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String operation;

    private String details;
}
