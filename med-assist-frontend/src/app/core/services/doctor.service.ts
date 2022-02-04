import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Doctor} from '../model/doctor';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class DoctorService {

    private doctorsUrl = (environment.backendBaseUrl + '/api/v1/doctors');
    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
    }

    getAllDoctors() {
        return this.http.get(this.doctorsUrl)
            .pipe(map(response => (response as unknown as Doctor[])));
    }

    countSpecialties() {
        return this.http.get(this.doctorsUrl + '/count/per-specialty')
            .pipe(map(response => (response as unknown as Map<string, number>)));
    }

}
