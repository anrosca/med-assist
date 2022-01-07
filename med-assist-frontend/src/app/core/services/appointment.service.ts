import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Appointment} from '../model/appointment';

@Injectable({
    providedIn: 'root'
})
export class AppointmentService {

    private appointmentsUrl = 'http://localhost:8080/api/v1/appointments';
    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
    }

    getAllAppointments() {
        return this.http.get(this.appointmentsUrl)
            .pipe(map(response => (response as unknown as Appointment[])));
    }

    countAllAppointments() {
        return this.http.get(this.appointmentsUrl + '/count')
            .pipe(map(response => (response as unknown as number)));
    }

    countOperations() {
        return this.http.get(this.appointmentsUrl + '/count/operations')
            .pipe(map(response => (response as unknown as Map<string, number>)));
    }

    countAppointmentsPerMonth() {
        return this.http.get(this.appointmentsUrl + '/count/per-month')
            .pipe(map(response => (response as unknown as Map<string, number>)));
    }

    deleteAppointment(appointmentId: string) {
        return this.http.delete(this.appointmentsUrl + '/' + appointmentId);
    }

    createAppointment(appointment: any) {
        return this.http.post(this.appointmentsUrl, {
            'color': appointment.color,
            'patientRequest' : {
                'firstName' : appointment.patientFirstName,
                'lastName' : appointment.patientLastName,
                'phoneNumber': appointment.patientPhoneNumber,
                'birthDate': appointment.patientBirthDate
            },
            'doctorId': appointment.doctorId,
            'patientId': appointment.patientId,
            'existingPatient': appointment.existingPatient,
            'startDate' : appointment.start,
            'endDate' : appointment.end,
            'operation': appointment.title,
            'details': appointment.details
        });
    }

}
