import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ContactUsComponent } from '../../../components/contact-us/contact-us.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SidenavService } from '../../../services/sidenav/sidenav.service';
import { AuthService } from '../../../services/auth/auth.service';
import { API_URL } from '../../../config';

@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

	placeHolder = 'Search for a book...';
	searchParams: FormGroup;

	roles: any = null;
	isAuth = false;


	constructor(public dialog: MatDialog,
				private sidenavService: SidenavService,
				private formBuilder: FormBuilder,
				private authService: AuthService) {
	}

	ngOnInit() {
		this.initForm();
		this.isAuthenticated();
	}

	initForm() {
		this.searchParams = this.formBuilder.group({
			searchValue: ''
		});
	}

	isAuthenticated() {
		this.roles = this.authService.getRoles();
		if (this.roles != null) {
			this.isAuth = true;
		}
	}

	login() {
		this.isAuthenticated();
		window.location.href = API_URL + '/login';
	}

	logout() {

		this.isAuthenticated();
	}

	search(searchParams: any) {
		console.log(searchParams.value.searchValue);
	}


	toggleSidenav() {
		this.sidenavService.toggle();
	}

	openDialog() {
		const dialogRef = this.dialog.open(ContactUsComponent, {});
	}

	checkPlaceHolder() {
		if (this.placeHolder) {
			this.placeHolder = null;
			return;
		} else {
			this.placeHolder = 'Search for a book...';
			return;
		}
	}
}
