import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../core/services/auth.service';
import {MatDialog} from '@angular/material';
import {ViewTokenModalComponent} from './view-token-modal/view-token-modal.component';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.css']
})
export class ProfileDetailsComponent implements OnInit {

  fullName: string;
  email: string;
  alias: string;
  roles: string;
  token: string;

  constructor(private authService: AuthenticationService, public dialog: MatDialog) { }

  ngOnInit() {
    this.fullName = this.authService.getCurrentUser().fullName;
    this.email = this.authService.getCurrentUser().email;
    this.roles = this.authService.getCurrentUser().roles;
    this.token = this.authService.getCurrentUser().token;
  }

  openDialog() {
    const dialogRef = this.dialog.open(ViewTokenModalComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
}
