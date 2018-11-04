import { AfterViewInit, Component, OnInit } from '@angular/core';
import { BookService } from '../../services/book/book.service';
import { PageEvent } from '@angular/material';

@Component({
	selector: 'app-listview',
	templateUrl: './listview.component.html',
	styleUrls: ['./listview.component.css']
})
export class ListviewComponent implements OnInit, AfterViewInit {

	// MatPaginator Inputs
	length: 100;
	pageSize = 10;
	pageSizeOptions: number[] = [5, 10, 25, 100];

	// MatPaginator Output
	pageEvent: PageEvent = new PageEvent;

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
		});
		this.pageEvent.pageIndex = 0;
		this.pageEvent.pageSize = 10;

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

	paginationFrom(pageEvent) {
		this.pageSize = pageEvent.pageSize;
		return ((pageEvent.pageIndex === 0) ? pageEvent.pageIndex : (pageEvent.pageIndex) * pageEvent.pageSize);
	}

	paginationTo(pageEvent) {
		return this.paginationFrom(pageEvent) + this.pageSize;
	}


	onPaginateEvent() {
		console.log(this.pageEvent);
	}
}
