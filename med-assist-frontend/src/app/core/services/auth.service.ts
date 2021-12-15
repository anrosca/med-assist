import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import * as jwt_decode from 'jwt-decode';
import * as moment from 'moment';
import 'rxjs/add/operator/delay';

import { environment } from '../../../environments/environment';
import { of, EMPTY } from 'rxjs';
import {User} from '../model/user';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    private authUrl = 'http://localhost:8080/api/v1/auth';
    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
    }

    login(username: string, password: string) {
        const url = `${this.authUrl}/login`;

        return this.http.post<User>(url, {username, password})
            .pipe(map((user) => {
                const decodedToken = jwt_decode(user.accessToken);

                this.localStorage.setItem('currentUser', JSON.stringify({
                    token: user.accessToken,
                    isAdmin: user.authorities.includes('ROLE_POWER_USER'),
                    roles: user.authorities,
                    username: user.username,
                    id: user.id,
                    alias: user.email.split('@')[0],
                    email: user.email,
                    expiration: new Date(decodedToken.exp * 1000),
                    fullName: user.firstName + ' ' + user.lastName,
                }));

                return true;
            }));
    }

    logout(): void {
        // clear token remove user from local storage to log user out
        this.localStorage.removeItem('currentUser');
    }

    getCurrentUser(): any {
        return JSON.parse(this.localStorage.getItem('currentUser'));
    }

    passwordResetRequest(username: string) {
        return of(true).delay(1000);
    }

    changePassword(username: string, currentPwd: string, newPwd: string) {
        return of(true).delay(1000);
    }

    passwordReset(username: string, token: string, password: string, confirmPassword: string): any {
        return of(true).delay(1000);
    }
}
