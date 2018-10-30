import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { API_URL } from '../../config';
import { catchError } from 'rxjs/internal/operators';

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

	getBooks() {
		return this.http.get(this.url);
	}

	saveBook(book: Book) {
		return this.http.post<Book>(this.url, book, this.httpOptions);
	}

	updateBook(book: Book, id: number) {
		book.id = id;
		return this.http.post<Book>(this.url, book, this.httpOptions);
	}

	removeBook(id: number) {
		return this.http.delete<Book>(this.url + '/' + id);
	}
}
