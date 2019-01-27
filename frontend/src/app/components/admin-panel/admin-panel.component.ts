import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit {

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
