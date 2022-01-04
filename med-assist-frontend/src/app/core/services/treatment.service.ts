import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { of, EMPTY } from 'rxjs';
import {User} from '../model/user';
import {Treatment} from "../model/treatment";

@Injectable({
    providedIn: 'root'
})
export class TreatmentService {

    private treatmentUrl = 'http://localhost:8080/api/v1/treatments';
    constructor(private http: HttpClient) {
    }

    getTreatmentsByToothId(toothId: string) {
        return this.http.get(this.treatmentUrl + '?toothId=' + toothId)
            .pipe(map(response => (response as unknown as Treatment[])));
    }

    getTreatmentsByPatientId(patientId: string) {
        return this.http.get(this.treatmentUrl + '?patientId=' + patientId)
            .pipe(map(response => (response as unknown as Treatment[])));
    }


    createTreatment(treatment: any) {
        return this.http.post(this.treatmentUrl, treatment)
    }

}

