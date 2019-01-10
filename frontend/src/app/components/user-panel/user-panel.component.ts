import { Component, OnInit } from '@angular/core';

@Component({
	selector: 'app-user-panel',
	templateUrl: './user-panel.component.html',
	styleUrls: ['./user-panel.component.scss']
})
export class UserPanelComponent implements OnInit {

  isAuth = false;
  role_admin = false;
  role_employee = false;

	constructor() {
    this.isAuth = (sessionStorage.getItem('authenticated') === 'true');
    this.role_admin = (sessionStorage.getItem('ROLE_ADMIN') === 'true');
    this.role_employee = (sessionStorage.getItem('ROLE_EMPLOYEE') === 'true');
	}

	ngOnInit() {
	}

}
