import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ContactUsComponent } from '../../../components/contact-us/contact-us.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SidenavService } from '../../../services/sidenav/sidenav.service';
import {AuthService} from '../../../services/auth/auth.service';
import {Router} from '@angular/router';

@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

	placeHolder = 'Search for a book...';
	searchParams: FormGroup;

  isAuth = false;
  role_admin = false;
  role_employee = false;

	constructor(public dialog: MatDialog,
				private sidenavService: SidenavService,
				private formBuilder: FormBuilder,
              private authService: AuthService,
              private router: Router) {
    this.isAuth = (sessionStorage.getItem('authenticated') === 'true');
    this.role_admin = (sessionStorage.getItem('ROLE_ADMIN') === 'true');
    this.role_employee = (sessionStorage.getItem('ROLE_EMPLOYEE') === 'true');
	}

	ngOnInit() {
		this.initForm();
	}

	initForm() {
		this.searchParams = this.formBuilder.group({
			searchValue: ''
		});
	}



  login() {
    this.router.navigateByUrl('/login');
  }

  logout() {
    this.authService.logoutUser();
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
