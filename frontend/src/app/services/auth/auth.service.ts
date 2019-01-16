import { Injectable } from '@angular/core';
import { MessageInfo } from '../../models/MessageInfo';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { API_URL } from '../../config';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import {Authorities} from '../../models/database/entites/Authorities';

@Injectable({
	providedIn: 'root'
})
export class AuthService {

	URL = API_URL + '/api/v1/';
  roleAdmin = false;
  roleUser = false;
  isAuth = false;
  name: string;
  isActual = false;


	constructor(private http: HttpClient,
				private router: Router,
              private cookieService: CookieService) {
	}

	 isAuthenticated() {
    return this.isAuth;
  }

   isAdmin() {

     console.log('in method isAdmin');

    return this.roleAdmin;
  }

   isUser() {
    return this.roleUser;
  }

   getUsername() {
    return this.name;
  }

  async isDataActual() {

	  console.log('in method isDataActual');

	  //if (this.isActual) {
	  //  return;
    //}

	  if (this.cookieService.get('JSESSIONID') == null) {
      this.isAuth = false;
      this.roleAdmin = false;
      this.roleUser = false;
      this.name = '';
    } else {
      const response: MessageInfo = await this.getOne('user', null, null) as MessageInfo;

      console.log(response);

      if (response && response.object) {
        this.setData(response);
      }
    }
  }

	setHeaders(username: string, password: string) {
    if (username == null || username === '') {
      return {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      };
    } else {
      return {
        headers: new HttpHeaders({
          'Authorization': 'Basic ' +
            btoa(username + ':' + password)
        })
      };
    }
	}

	async getOne(url: string, username: string, password: string) {
	  try {
	    if (username == null || username === '') {

        console.log('in get method');

        return await this.http.get(this.URL + url, {withCredentials: true}).toPromise();
      } else {
        return await this.http.get(this.URL + url, this.setHeaders(username, password)).toPromise();
      }
    } catch (e) {
      this.isAuth = false;
      this.roleAdmin = false;
      this.roleUser = false;
      this.name = '';
      return null;
    }
    return null;
	}

	async loginUser(username: string, password: string) {
    const response: MessageInfo = await this.getOne('user', username, password) as MessageInfo;
    if (response && response.object) {
      const token = response.message[0];
      this.cookieService.set('JSESSIONID', token);
      this.setData(response);
      this.isActual = true;
      this.reload().then(() => {
        this.router.navigateByUrl('/homepage');
      });
    } else {
      this.isAuth = false;
      this.roleAdmin = false;
      this.roleUser = false;
      this.name = '';
    }
	}

	async reload() {
    location.reload();
  }

	setData(response: MessageInfo) {

	  console.log('in method setData');

    this.name = response.object.username;
    this.isAuth = true;
    const roles: Authorities[] = response.object.authorities;
    for (let i = 0; i < roles.length; i++) {
      if (roles[i].authority === 'ROLE_ADMIN') {
        this.roleAdmin = true;
      } else if (roles[i].authority === 'ROLE_EMPLOYEE') {
        this.roleUser = true;
      }
    }
    this.isActual = true;

    console.log('after method setData');

  }

	logoutUser() {
	  this.cookieService.delete('JSESSIONID');
	  this.isAuth = false;
	  this.roleAdmin = false;
	  this.roleUser = false;
	  this.name = '';
	  window.location.href = API_URL + '/logout';
	}
}
