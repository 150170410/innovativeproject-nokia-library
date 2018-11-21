import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs/index';
import { catchError } from 'rxjs/internal/operators';
import { API_URL } from '../../config';
import { BookDetails } from '../../models/entites/BookDetails';
import { BookDetailsDTO } from '../../models/DTOs/BookDetailsDTO';

@Injectable({
	providedIn: 'root'
})
export class BookDetailsService {

	url = API_URL + '/api/v1/bookDetails';

	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};

	constructor(private http: HttpClient) {
	}

	async getBooks(id?: number) {
		const url = id ? this.url + '/getOne' + `/${id}` : this.url + '/getAll';
		return await this.http.get<BookDetails[]>(url).toPromise();
	}

	saveBook(book: BookDetailsDTO): Observable<BookDetails> {
		return this.http.post<BookDetails>(this.url, book, this.httpOptions)
		.pipe(
			catchError(this.handleError)
		);
	}

	updateBook(book: BookDetailsDTO, id: number) {
		return this.http.post<BookDetails>(this.url + `/${id}`, book, this.httpOptions)
		.pipe(
			catchError(this.handleError)
		);
	}

	removeBook(id: number) {
		return this.http.delete<BookDetails>(this.url + `/${id}`)
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
