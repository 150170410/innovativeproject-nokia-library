import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ContactUsComponent } from '../contact-us/contact-us.component';
import { SidenavService } from '../../services/sidenav/sidenav.service';

@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

	placeHolder = 'Search for a book...';

	constructor(public dialog: MatDialog,
				private sidenavService: SidenavService) {
	}

	ngOnInit() {
	}

	toggleSidenav() {
		this.sidenavService.toggle();
	}

	openDialog() {
		const dialogRef = this.dialog.open(ContactUsComponent, {
			width: '600px',
		});
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
