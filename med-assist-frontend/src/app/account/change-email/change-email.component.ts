import { NotificationService } from '../../core/services/notification.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { NGXLogger } from 'ngx-logger';

import { AuthenticationService } from '../../core/services/auth.service';
import { SpinnerService } from '../../core/services/spinner.service';

@Component({
  selector: 'app-change-email',
  templateUrl: './change-email.component.html',
  styleUrls: ['./change-email.component.css']
})
export class ChangeEmailComponent implements OnInit {
  form: FormGroup;
  currentEmail: string;
  newEmail: string;
  disableSubmit: boolean;

  constructor(private authService: AuthenticationService,
    private logger: NGXLogger,
    private spinnerService: SpinnerService,
    private notificationService: NotificationService) {
  }

  ngOnInit() {
    this.currentEmail = this.authService.getCurrentUser().email;
    this.form = new FormGroup({
      currentEmail: new FormControl(this.currentEmail),
      newEmail: new FormControl('', Validators.required),
    });
    this.form.get('newEmail').valueChanges
      .subscribe(val => { this.newEmail = val; });
    this.spinnerService.visibility.subscribe((value) => {
      this.disableSubmit = value;
    });
  }

  changeEmail() {

    if (this.newEmail === this.currentEmail) {
      this.notificationService.openSnackBar('New email must be different from the previous one.');
      return;
    }

    const currentUser = this.authService.getCurrentUser();

    this.authService.changeEmail(currentUser.id, this.newEmail)
      .subscribe(
        data => {
          this.logger.info(`User ${currentUser.id} changed email.`);
          this.notificationService.openSnackBar('Your email has been changed.');
          this.form.get('currentEmail').setValue(this.newEmail);
          this.form.get('newEmail').reset();
        },
        error => {
          this.notificationService.openSnackBar(error.error);
        }
      );
  }
}
