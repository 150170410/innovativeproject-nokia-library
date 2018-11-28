import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ContactUsComponent } from '../../../components/contact-us/contact-us.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SidenavService } from '../../../services/sidenav/sidenav.service';

@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

	placeHolder = 'Search for a book...';
	searchParams: FormGroup;


	constructor(public dialog: MatDialog,
				private sidenavService: SidenavService,
				private formBuilder: FormBuilder) {
	}

	ngOnInit() {
		this.initForm();
	}

	initForm() {
		this.searchParams = this.formBuilder.group({
			searchValue: ''
		});
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
