import { ErrorHandler, Injectable, Injector } from '@angular/core';
import {Router} from '@angular/router';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
    constructor(private injector: Injector) { }

    handleError(error: Error) {
        const router = this.injector.get(Router);
        console.log(error.stack.toString());
    }
}
