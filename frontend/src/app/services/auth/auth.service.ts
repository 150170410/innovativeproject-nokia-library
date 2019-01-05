import { Injectable } from '@angular/core';
import {MessageInfo} from '../../models/MessageInfo';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {API_URL} from '../../config';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';
import {Authorities} from '../../models/database/entites/Authorities';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isAuth = false;
  URL = API_URL + '/api/v1/';

  constructor(private http: HttpClient,
              private router: Router) { }

  setHeaders() {
    return {
      headers: new HttpHeaders({ 'Authorization': 'Basic ' +
          btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))})
    };
  }

  async getOne(url: string) {
    return await this.http.get<MessageInfo>(this.URL + url, this.setHeaders())
      .pipe(
        catchError(this.handleError)
      ).toPromise();
  }

  logoutUser() {
    sessionStorage.clear();
    sessionStorage.setItem('ROLE_ADMIN', 'false');
    sessionStorage.setItem('ROLE_EMPLOYEE', 'false');
    sessionStorage.setItem('authenticated', 'false');
    this.router.navigateByUrl('/homepage');
    location.reload();
  }

  async loginUser() {
    const response: MessageInfo = await this.getOne('user') as MessageInfo;
    if (response.object) {
      sessionStorage.setItem('authenticated', 'true');
      const roles: Authorities[] = response.object.authorities;
      for (let i = 0 ; i < roles.length; i++) {
        if (roles[i].authority === 'ROLE_ADMIN') {
          sessionStorage.setItem('ROLE_ADMIN', 'true');
        } else if (roles[i].authority === 'ROLE_EMPLOYEE') {
          sessionStorage.setItem('ROLE_EMPLOYEE', 'true');
        }
      }
      location.reload();
      this.router.navigateByUrl('/homepage');
    } else {
      sessionStorage.clear();
      sessionStorage.setItem('ROLE_ADMIN', 'false');
      sessionStorage.setItem('ROLE_EMPLOYEE', 'false');
      sessionStorage.setItem('authenticated', 'false');
    }
  }

  private handleError(error: HttpErrorResponse) {
    sessionStorage.clear();
    sessionStorage.setItem('ROLE_ADMIN', 'false');
    sessionStorage.setItem('ROLE_EMPLOYEE', 'false');
    sessionStorage.setItem('authenticated', 'false');
    return null;
  }
}
