package inc.evil.medassist.appointment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class AppointmentColor {
    @Column(name = "primary_color")
    private String primary;

    @Column(name = "secondary_color")
    private String secondary;
}
