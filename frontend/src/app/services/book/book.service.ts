import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { API_URL } from '../../config';
import { catchError, retry } from 'rxjs/internal/operators';
import { Observable, throwError } from 'rxjs/index';
import { resolve } from 'q';

export class Book {
	id: number;
	title: string;
	authorName: string;
	authorSurname: string;
}


@Injectable({
	providedIn: 'root'
})
export class BookService {
	url = API_URL + '/api/v1/library/books';
	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};

	constructor(private http: HttpClient) {
	}

	getBooks(id?: number) {
		let url;
		if (!!id) {
			url = this.url + '/' + id;
		} else {
			url = this.url;
		}

		this.http.get(url)
		.pipe(
			retry(3),
			catchError(this.handleError)
		);

	}

	async getABooks(id?: number) {
		let url;
		if (!!id) {
			url = this.url + '/' + id;
		} else {
			url = this.url;
		}
		let data;
		await this.http.get(url).subscribe((res) =>{
			data = res;
		});
		return data;
	}

	saveBook(book: Book): Observable<Book> {
		return this.http.post<Book>(this.url, book, this.httpOptions)
		.pipe(
			catchError(this.handleError)
		);
	}

	updateBook(book: Book, id: number) {
		book.id = id;
		return this.http.post<Book>(this.url, book, this.httpOptions)
		.pipe(
			catchError(this.handleError)
		);
	}

	removeBook(id: number) {
		return this.http.delete<Book>(this.url + '/' + id)
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
