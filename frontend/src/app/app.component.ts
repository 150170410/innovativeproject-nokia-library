import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material';
import { SidenavService } from './services/sidenav/sidenav.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

	@ViewChild('sidenav') public sidenav: MatSidenav;

	constructor(private sidenavService: SidenavService) {
	}

	ngOnInit(): void {
		this.sidenavService.setSidenav(this.sidenav);
	}
}
