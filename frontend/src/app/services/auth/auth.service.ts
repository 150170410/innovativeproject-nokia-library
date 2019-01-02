import {Injectable} from '@angular/core';
import {RestService} from '../rest/rest.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: RestService) {
  }

  async getRoles() {
    const response = await this.http.getProperties('user/get');
	  if (response.object === null) {
      return null;
    } else {
      return response.object.authorities;
    }
  }

  login() {

  }

}
