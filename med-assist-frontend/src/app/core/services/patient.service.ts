import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Patient} from '../model/patient';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class PatientService {

    private patientsUrl = (environment.backendBaseUrl + '/api/v1/patients');
    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
    }

    getAllPatients() {
        return this.http.get(this.patientsUrl)
            .pipe(map(response => (response as unknown as Patient[])));
    }

    countAgeCategories() {
        return this.http.get(this.patientsUrl + '/count/per-age-category')
            .pipe(map(response => response as unknown as Map<string, number>));
    }

    countPatientsCreatedPerMonth() {
        return this.http.get(this.patientsUrl + '/count/per-month')
            .pipe(map(response => response as unknown as Map<string, number>));
    }

    createPatient(patient: any) {
        return this.http.post(this.patientsUrl, {
            'firstName': patient.firstName,
            'lastName': patient.lastName,
            'phoneNumber': patient.phoneNumber,
            'birthDate': patient.birthDate,
            'source': patient.source
        });
    }

}
