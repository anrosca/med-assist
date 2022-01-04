package inc.evil.medassist.appointment.web;

import inc.evil.medassist.appointment.model.AppointmentColor;

import javax.validation.constraints.NotNull;

public record Color(@NotNull String primary, @NotNull String secondary) {
    public static Color from(AppointmentColor color) {
        return new Color(color.getPrimary(), color.getSecondary());
    }

    public AppointmentColor toAppointmentColor() {
        return new AppointmentColor(primary, secondary);
    }
}
