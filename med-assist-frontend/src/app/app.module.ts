import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppComponent} from './app.component';
import {CoreModule} from './core/core.module';
import {SharedModule} from './shared/shared.module';
import {CustomMaterialModule} from './custom-material/custom-material.module';
import {AppRoutingModule} from './app-routing.module';
import {LoggerModule} from 'ngx-logger';
import {environment} from '../environments/environment';
import {ViewTokenModalComponent} from './account/profile-details/view-token-modal/view-token-modal.component';
import {AccountModule} from './account/account.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {NgApexchartsModule} from 'ng-apexcharts';


@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FontAwesomeModule,
        CoreModule,
        SharedModule,
        CustomMaterialModule.forRoot(),
        AppRoutingModule,
        AccountModule,
        NgApexchartsModule,
        LoggerModule.forRoot({
            serverLoggingUrl: `http://my-api/logs`,
            level: environment.logLevel,
            serverLogLevel: environment.serverLogLevel
        }),
        NgbModule
    ],
    exports: [],
    bootstrap: [AppComponent],
    entryComponents: [ViewTokenModalComponent]
})
export class AppModule {
}
