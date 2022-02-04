import {Injectable, Inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import * as jwt_decode from 'jwt-decode';

import {User} from '../model/user';
import {of} from 'rxjs';
import {delay, map} from 'rxjs/operators';
import {UserService} from './user.service';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    private authUrl = (environment.backendBaseUrl + '/api/v1/auth');

    constructor(private http: HttpClient,
                @Inject('LOCALSTORAGE') private localStorage: Storage, private userService: UserService) {
    }

    login(username: string, password: string) {
        const url = `${this.authUrl}/login`;

        return this.http.post<User>(url, {username, password})
            .pipe(map(user => {
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
        return of(true).pipe(delay(1000));
    }

    changePassword(username: string, currentPwd: string, newPwd: string) {
        return this.userService.changePassword(this.getCurrentUser().id, newPwd).pipe(delay(1000));
    }

    passwordReset(username: string, token: string, password: string, confirmPassword: string): any {
        return of(true).pipe(delay(1000));
    }
}
