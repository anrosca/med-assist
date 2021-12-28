import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { of, EMPTY } from 'rxjs';
import {User} from '../model/user';
import {Doctor} from "../model/doctor";
import {Patient} from "../model/patient";

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

}
