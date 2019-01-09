import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/internal/operators';
import { API_URL } from '../../config';
import { Observable, throwError } from 'rxjs/index';
import { MessageInfo } from '../../models/MessageInfo';

@Injectable({
	providedIn: 'root'
})
export class RestService {
	URL = API_URL + '/api/v1/';

	setHeaders() {
		if (!sessionStorage.getItem('username')) {
			return {
				headers: new HttpHeaders({})
			};
		}
		return {
			headers: new HttpHeaders({
				'Authorization': 'Basic ' +
				btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))
			})
		};
	}

	constructor(private http: HttpClient) {
	}

	async getAll(url: string) {
		return await this.http.get<MessageInfo>(this.URL + url, this.setHeaders())
		.pipe(
			catchError(this.handleError)
		).toPromise();
	}

	save(url: string, item: any): Observable<any> {
		return this.http.post<any>(this.URL + url + '/create', item, this.setHeaders())
		.pipe(
			catchError(this.handleError)
		);
	}

	update(url: string, id: number, item: any) {
		return this.http.post<any>(this.URL + url + '/update/' + id, item, this.setHeaders())
		.pipe(
			catchError(this.handleError)
		);
	}

	remove(url: string, id: number) {
		return this.http.delete<any>(this.URL + url + '/remove/' + id, this.setHeaders())
		.pipe(
			catchError(this.handleError)
		);
	}

	private handleError(error: HttpErrorResponse) {

		console.log('ja 2');

		if (error.error instanceof ErrorEvent) {
			console.error('An error occurred:', error.error.message);
		} else {
			console.error(
				`Backend returned code ${error.status}, ` +
				`exception was: ${error.error.message}`);
		}
		return throwError(error);
	}

}
