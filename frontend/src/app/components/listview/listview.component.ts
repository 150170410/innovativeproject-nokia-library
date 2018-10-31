import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Book, BookService } from '../../services/book/book.service';
import { HttpClient } from '@angular/common/http';

@Component({
	selector: 'app-listview',
	templateUrl: './listview.component.html',
	styleUrls: ['./listview.component.css']
})
export class ListviewComponent implements OnInit, AfterViewInit {


	books: any;
	errorMessage: any;
	fakeBooks = [
		{ 'id': 1, 'title': 'title 1', 'authorName': 'name1', 'authorSurname': 'surname1' },
		{ 'id': 2, 'title': 'title 2', 'authorName': 'name2', 'authorSurname': 'surname2' },
		{ 'id': 3, 'title': 'title 3', 'authorName': 'name2', 'authorSurname': 'surname2' }
	];
	newBook = { 'id': null, 'title': 'book ' + Math.floor(Math.random() * 100), 'authorName': 'name ' + Math.floor(Math.random() * 100), 'authorSurname': 'surname' + Math.floor(Math.random() * 100) };

	constructor(private bookService: BookService) {
	}

	ngOnInit() {

		this.bookService.saveBook(this.newBook).subscribe((response) => {
			console.log('book added to database');
		});
		this.bookService.updateBook(this.newBook, 1).subscribe((response) => {
			console.log('book updated');
			console.log('updating is broken on backend side though');
		});

		// this.bookService.removeBook(3).subscribe();

		// this.bookService.getBooks().subscribe((response) => {
		// 	this.books = response;
		// 	console.log(this.books);
		// });

		// this.bookService.getBooks(99999).subscribe((response) => {
		// 	// this.books = response;
		// 	console.log(response);
		// });
	}

	ngAfterViewInit(): void {
		this.getBooks();
	}

	async getBooks(id?: number) {
		this.books = await this.bookService.getBooks(id)
		.catch((err) => {
			console.log(err.message);
			this.errorMessage = err;
		});
	}


}
