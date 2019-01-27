import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';

@Component({
	selector: 'app-user-panel',
	templateUrl: './user-panel.component.html',
	styleUrls: ['./user-panel.component.scss']
})
export class UserPanelComponent implements OnInit {

  isAuth = false;
  role_admin = false;
  role_employee = false;

	constructor(private authService: AuthService) {
	  this.initData();
  }

  initData() {
    this.authService.getUserData().then( () => {
      this.isAuth = this.authService.isAuthenticated();
      this.role_admin = this.authService.isAdmin();
      this.role_employee = this.authService.isUser();
    });
  }

	ngOnInit() {
	}
}
