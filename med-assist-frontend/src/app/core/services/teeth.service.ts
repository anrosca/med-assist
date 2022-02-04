import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { of, EMPTY } from 'rxjs';
import {User} from '../model/user';

@Injectable({
    providedIn: 'root'
})
export class TeethService {

    private teethUrl = (environment.backendBaseUrl + '/api/v1/teeth');
    constructor(private http: HttpClient) {
    }

    getPatientTeeth(patientId: string) {
        return this.http.get(this.teethUrl + '?patientId=' + patientId)
            .pipe(map(response => (response as unknown as User[])));
    }

}

