import { Injectable } from '@angular/core';

@Injectable({
	providedIn: 'root'
})
export class ThemeService {

	constructor() {
	}

	public isDarkTheme(): boolean {
		if (localStorage.getItem('theme') == 'light') {
			return false;
		} else if (localStorage.getItem('theme') == 'dark') {
			return true;
		} else {
			return false;
		}
	}
}
