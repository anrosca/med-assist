import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AuthenticationService} from '../../../core/services/auth.service';

@Component({
  selector: 'app-view-token-modal',
  templateUrl: './view-token-modal.component.html',
  styleUrls: ['./view-token-modal.component.css']
})
export class ViewTokenModalComponent implements OnInit {

  token: string;

  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
    this.token = this.authService.getCurrentUser().token;
  }

}
