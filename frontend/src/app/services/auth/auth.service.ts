import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { API_URL } from '../../config';
import {MessageInfo} from '../../models/MessageInfo';
import {RestService} from '../rest/rest.service';
import {Router} from '@angular/router';
import {Authorities} from '../../models/database/entites/Authorities';

@Injectable({
	providedIn: 'root'
})
export class AuthService {

  username: string;
  isSignedCorrectly: boolean;
  roleAdmin = false;
  roleUser = false;
  isAuth = false;
  name: string;
  isActual = false;

	constructor(private http: HttpClient,
              private restService: RestService,
              private router: Router) {
	}

	async loginUser(username: string, password: string) {
    let body = new HttpParams();
    body = body.set('username', username);
    body = body.set('password', password);
    try {
      await this.http.post(API_URL + '/login', body, {withCredentials: true}).toPromise();
      this.isSignedCorrectly = true;

      this.reload().then(() => {
        this.router.navigateByUrl('/homepage');
      });

    } catch (e) {
      this.isSignedCorrectly = false;
    }
	}
  async reload() {
    location.reload();
  }

  isSigned() {
    return this.isSignedCorrectly;
  }
  isAuthenticated() {
    return this.isAuth;
  }

  isAdmin() {
    return this.roleAdmin;
  }

  isUser() {
    return this.roleUser;
  }

  getUsername() {
    return this.name;
  }

  async getUserData() {
	  if (this.isActual === false) {
      this.isActual = true;
      const response: MessageInfo = await this.restService.getAll('user');
      if (response && response.object) {
        this.name = response.object.name;
        this.isAuth = true;
        const roles: Authorities[] = response.object.authorities;
        for (let i = 0; i < roles.length; i++) {
          if (roles[i].authority === 'ROLE_ADMIN') {
            this.roleAdmin = true;
          } else if (roles[i].authority === 'ROLE_EMPLOYEE') {
            this.roleUser = true;
          }
        }
      } else {
        console.log('User is not logged!');
        this.isAuth = false;
        this.roleAdmin = false;
        this.roleUser = false;
        this.name = '';
      }
    }
  }

	logoutUser() {
    this.isAuth = false;
    this.roleAdmin = false;
    this.roleUser = false;
    this.name = '';
    window.location.href = API_URL + '/logout';
	}
}
