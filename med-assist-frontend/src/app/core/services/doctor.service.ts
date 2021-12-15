import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import * as jwt_decode from 'jwt-decode';
import * as moment from 'moment';
import 'rxjs/add/operator/delay';

import { environment } from '../../../environments/environment';
import { of, EMPTY } from 'rxjs';
import {User} from '../model/user';
import {Doctor} from "../model/doctor";

@Injectable({
    providedIn: 'root'
})
export class DoctorService {

    private doctorsUrl = 'http://localhost:8080/api/v1/doctors';
    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
    }

    getAllDoctors() {
        return this.http.get<PagedUserViewResponse>(this.doctorsUrl)
            .pipe(map(response => (response as unknown as Doctor[])));
    }

}

export interface PagedUserViewResponse {
    _embedded: any;
}
