import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Patient} from '../model/patient';

@Injectable({
    providedIn: 'root'
})
export class PatientService {

    private patientsUrl = 'http://localhost:8080/api/v1/patients';
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

}
