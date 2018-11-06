import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { API_URL } from '../../config';
import { catchError } from 'rxjs/internal/operators';
import { BehaviorSubject, Observable, throwError } from 'rxjs/index';
import { Book } from '../../entities/Book';


@Injectable({
	providedIn: 'root'
})
export class BookService {

	url = API_URL + '/api/v1/library/books';
	private behaviorSubject: BehaviorSubject<Book[]> = new BehaviorSubject([]);
	public readonly books: Observable<Book[]> = this.behaviorSubject.asObservable();


	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};

	constructor(private http: HttpClient) {
	}

	async getBooks(id?: number) {
		const url = id ? this.url + `/${id}` : this.url;
		return await this.http.get<Book[]>(url).toPromise();
	}

	saveBook(book: Book): Observable<Book> {
		return this.http.post<Book>(this.url, book, this.httpOptions)
		.pipe(
			catchError(this.handleError)
		);
	}

	updateBook(book: Book, id: number) {
		return this.http.post<Book>(this.url + `/${id}`, book, this.httpOptions)
		.pipe(
			catchError(this.handleError)
		);
	}

	removeBook(id: number) {
		return this.http.delete<Book>(this.url + `/${id}`)
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
