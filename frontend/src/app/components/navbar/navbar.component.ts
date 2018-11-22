import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ContactUsComponent } from '../contact-us/contact-us.component';

@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

	constructor(public dialog: MatDialog) {
	}

	ngOnInit() {
	}

	openDialog() {
		const dialogRef = this.dialog.open(ContactUsComponent, {
			width: '600px',
		});
	}
}
