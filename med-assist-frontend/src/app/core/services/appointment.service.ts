import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { of, EMPTY } from 'rxjs';
import {User} from '../model/user';
import {Doctor} from "../model/doctor";
import {CalendarEvent} from "angular-calendar";
import {Appointment} from "../model/appointment";

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

    deleteAppointment(appointmentId: string){
        return this.http.delete(this.appointmentsUrl + '/' + appointmentId);
    }

    createAppointment(appointment: any) {
        return this.http.post(this.appointmentsUrl, {
            "color": appointment.color,
            "patientRequest" : {
                "firstName" : appointment.patientFirstName,
                "lastName" : appointment.patientLastName,
                "phoneNumber": appointment.patientPhoneNumber,
                "birthDate": appointment.patientBirthDate
            },
            "doctorId": appointment.doctorId,
            "patientId": appointment.patientId,
            "existingPatient": appointment.existingPatient,
            "startDate" : appointment.start,
            "endDate" : appointment.end,
            "operation": appointment.title,
            "details": appointment.details
        })
    }

}
