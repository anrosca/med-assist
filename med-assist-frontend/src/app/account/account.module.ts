import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountRoutingModule } from './account-routing.module';
import { ProfileComponent } from './profile/profile.component';
import { SharedModule } from '../shared/shared.module';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ProfileDetailsComponent } from './profile-details/profile-details.component';
import { ViewTokenModalComponent } from './profile-details/view-token-modal/view-token-modal.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    AccountRoutingModule
  ],
  declarations: [ProfileComponent, ChangePasswordComponent, ProfileDetailsComponent, ViewTokenModalComponent],
  exports: [ProfileComponent, ProfileDetailsComponent, ViewTokenModalComponent]
})
export class AccountModule { }
