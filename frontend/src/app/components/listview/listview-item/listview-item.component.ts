import { Component, Input, OnInit } from '@angular/core';
import { BookDetails } from '../../../models/database/entites/BookDetails';
import {AuthService} from '../../../services/auth/auth.service';

@Component({
	selector: 'app-listview-item',
	templateUrl: './listview-item.component.html',
	styleUrls: ['./listview-item.component.css']
})
export class ListviewItemComponent implements OnInit {

  isAuth = false;
  role_admin = false;
  role_employee = false;

	@Input() bookDetails: BookDetails;

	constructor(private authService: AuthService) {
    this.initAuthVariables();
	}

  initAuthVariables() {
    this.authService.isDataActual().then(() => {
      this.isAuth = this.authService.isAuthenticated();
      this.role_admin = this.authService.isAdmin();
      this.role_employee = this.authService.isUser();
    });
  }

	ngOnInit() {
	}

}
