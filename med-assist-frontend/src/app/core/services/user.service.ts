import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

import {User} from '../model/user';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private usersUrl = (environment.backendBaseUrl + '/api/v1/users');
    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
    }

    getAllUsers() {
        return this.http.get(this.usersUrl)
            .pipe(map(response => (response as unknown as User[])));
    }

    changePassword(userId: string, newPwd: string) {
        return this.http.put(this.usersUrl + '/' + userId, {'password': newPwd})
            .pipe(map(response => (response as unknown as User)));
    }
}

