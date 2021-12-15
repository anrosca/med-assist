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
export class UserService {

    private usersUrl = 'http://localhost:8080/api/v1/users';
    constructor(private http: HttpClient,
        @Inject('LOCALSTORAGE') private localStorage: Storage) {
    }

    getAllUsers() {
        return this.http.get<PagedUserViewResponse>(this.usersUrl)
            .pipe(map(response => (response as unknown as User[])));
    }

}

export interface PagedUserViewResponse {
    _embedded: any;
}
