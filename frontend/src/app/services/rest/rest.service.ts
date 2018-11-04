import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Book } from '../book/book.service';
import { catchError } from 'rxjs/internal/operators';
import { API_URL } from '../../config';
import { Observable, throwError } from 'rxjs/index';

@Injectable({
	providedIn: 'root'
})
export class RestService {
	URL = API_URL + '/api/v1/library';
	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};

	constructor(private http: HttpClient) {
	}

	async getAll(url: string, id?: number) {
		let u;
		if (!!id) {
			u = this.URL + url + `/${id}`;
		} else {
			u = this.URL + url;
		}
		return await this.http.get<any[]>(u).toPromise();
	}

	save(url: string, book: any): Observable<Book> {
		return this.http.post<any>(this.URL + url, book, this.httpOptions)
		.pipe(
			catchError(this.handleError)
		);
	}

	update(url: string, book: any, id: number) {
		return this.http.post<any>(this.URL + url + `/${id}`, book, this.httpOptions)
		.pipe(
			catchError(this.handleError)
		);
	}

	remove(url: string, id: number) {
		return this.http.delete<any>(this.URL + url + `/${id}`)
		.pipe(
			catchError(this.handleError)
		);
	}

	private handleError(error: HttpErrorResponse) {
		if (error.error instanceof ErrorEvent) {
			console.error('An error occurred:', error.error.message);
		} else {
			console.error(
				`Backend returned code ${error.status}, ` +
				`body was: ${error.error}`);
		}
		return throwError(
			'Something bad happened; please try again later.');
	}

}
