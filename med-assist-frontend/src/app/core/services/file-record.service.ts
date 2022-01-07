import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Patient} from '../model/patient';
import {FileRecord} from "../model/file-record";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class FileRecordService {

    private filesUrl = 'http://localhost:8080/api/v1/files';

    constructor(private http: HttpClient,
                @Inject('LOCALSTORAGE') private localStorage: Storage) {
    }

    getByPatientId(patientId: string) {
        return this.http.get(this.filesUrl + '?patientId=' + patientId)
            .pipe(map(response => (response as unknown as FileRecord[])));
    }

    getById(id: string) {
        return this.http.get(this.filesUrl + '/' + id)
            .pipe(map(response => (response as unknown as FileRecord[])));
    }

    deleteById(id: string) {
        return this.http.delete(this.filesUrl + '/' + id).pipe();
    }

    download(url: string): Observable<Blob>  {
        return this.http.get(url, {responseType: 'blob'}).pipe();
    }

    upload(file: File, patientId: string) {
        const formData: FormData = new FormData();
        formData.append('file', file);
        formData.append('patientId', patientId);
        return this.http.post(this.filesUrl, formData).pipe();
    }

}
