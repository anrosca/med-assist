package inc.evil.medassist.appointment.service;

public class ConflictingAppointmentsException extends RuntimeException {
    public ConflictingAppointmentsException(String message) {
        super(message);
    }
}
