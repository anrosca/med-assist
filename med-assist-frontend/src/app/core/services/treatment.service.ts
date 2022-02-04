import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Treatment} from '../model/treatment';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TreatmentService {

    private treatmentUrl = (environment.backendBaseUrl + '/api/v1/treatments');
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

    getAllTreatments() {
        return this.http.get(this.treatmentUrl)
            .pipe(map(response => (response as unknown as Treatment[])));
    }

    createTreatment(treatment: any) {
        return this.http.post(this.treatmentUrl, treatment)
    }

}

