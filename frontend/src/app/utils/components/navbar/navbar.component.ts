import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ContactUsComponent } from '../../../components/contact-us/contact-us.component';
import { AuthService } from '../../../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

	isAuth = false;
	role_admin = false;
	role_employee = false;
	loggedAs = '';

	@Output() changedTheme = new EventEmitter<string>();

	constructor(public dialog: MatDialog,
				private authService: AuthService,
				private router: Router) {
		this.isAuth = (sessionStorage.getItem('authenticated') === 'true');
		this.role_admin = (sessionStorage.getItem('ROLE_ADMIN') === 'true');
		this.role_employee = (sessionStorage.getItem('ROLE_EMPLOYEE') === 'true');
		this.loggedAs = sessionStorage.getItem('username');
	}

	ngOnInit() {
	}

	login() {
		this.router.navigateByUrl('/login');
	}

	logout() {
		this.authService.logoutUser();
	}

	register() {
		this.router.navigateByUrl('/register')
	}

	openDialog() {
		const dialogRef = this.dialog.open(ContactUsComponent, {});
	}

	changeTheme() {
		if (localStorage.getItem('theme') == 'light') {
			this.changedTheme.emit('dark');
		} else if (localStorage.getItem('theme') == 'dark') {
			this.changedTheme.emit('light');
		} else {
			this.changedTheme.emit('dark');
		}
	}
}
