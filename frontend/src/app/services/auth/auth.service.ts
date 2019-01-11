import { Injectable } from '@angular/core';
import { MessageInfo } from '../../models/MessageInfo';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { API_URL } from '../../config';
import { Router } from '@angular/router';
import { Authorities } from '../../models/database/entites/Authorities';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
	providedIn: 'root'
})
export class AuthService {

	URL = API_URL + '/api/v1/';

	constructor(private http: HttpClient,
				private router: Router,
              private cookieService: CookieService) {
	}

	setHeaders() {
		return {
			headers: new HttpHeaders({
				'Authorization': 'Basic ' +
				btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))
			})
		};
	}

	setSomething(url: string) {
    this.http.get(API_URL + '/login', this.setHeaders()).toPromise().then(
      (res: Response) => console.log(res));
  }

	async getOne(url: string) {
	  try {
      return await this.http.get(this.URL + url, this.setHeaders()).toPromise().then(res => {
        return res;
      });
    } catch (e) {
      sessionStorage.clear();
      sessionStorage.setItem('ROLE_ADMIN', 'false');
      sessionStorage.setItem('ROLE_EMPLOYEE', 'false');
      sessionStorage.setItem('authenticated', 'false');
      sessionStorage.setItem('isSignedCorrectly', 'false');
      return null;
    }
	}

	async loginUser() {

    const response: MessageInfo = await this.getOne('user') as MessageInfo;
    //this.setSomething('user');
    if (response && response.object) {
      sessionStorage.setItem('authenticated', 'true');
      const roles: Authorities[] = response.object.authorities;
      for (let i = 0; i < roles.length; i++) {
        if (roles[i].authority === 'ROLE_ADMIN') {
          sessionStorage.setItem('ROLE_ADMIN', 'true');
        } else if (roles[i].authority === 'ROLE_EMPLOYEE') {
          sessionStorage.setItem('ROLE_EMPLOYEE', 'true');
        }
      }

      /*
      this.router.navigateByUrl('/homepage')
        .then(() => {
          sessionStorage.setItem('isSignedCorrectly', 'true');
          location.reload();
        });
        */
    } else {
      sessionStorage.clear();
      sessionStorage.setItem('ROLE_ADMIN', 'false');
      sessionStorage.setItem('ROLE_EMPLOYEE', 'false');
      sessionStorage.setItem('authenticated', 'false');
      sessionStorage.setItem('isSignedCorrectly', 'false');
    }
	}

	logoutUser() {
		sessionStorage.clear();
		sessionStorage.setItem('ROLE_ADMIN', 'false');
		sessionStorage.setItem('ROLE_EMPLOYEE', 'false');
		sessionStorage.setItem('authenticated', 'false');
    sessionStorage.setItem('isSignedCorrectly', 'true');

		this.router.navigateByUrl('/homepage')
		.then(() => {
			location.reload();
		});
	}
}
