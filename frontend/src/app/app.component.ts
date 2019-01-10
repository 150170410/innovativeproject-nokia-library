import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

	@ViewChild('sidenav') public sidenav: MatSidenav;
	darkTheme: boolean;

	constructor() {
	}

	ngOnInit(): void {
		this.darkTheme = this.getTheme();
	}

	changedTheme(theme: string) {
		if (theme === 'dark') {
			localStorage.setItem('theme', 'dark');
			this.darkTheme = true;
		} else if (theme === 'light') {
			localStorage.setItem('theme', 'light');
			this.darkTheme = false;
		} else {
			localStorage.setItem('theme', 'dark');
			this.darkTheme = true;
		}
	}

	getTheme(): boolean {
		if (localStorage.getItem('theme') == 'light') {
			return false;
		} else if(localStorage.getItem('theme') == 'dark') {
			return true;
		} else {
			return false;
		}
	}
}
