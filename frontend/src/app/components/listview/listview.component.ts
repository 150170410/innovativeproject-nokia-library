import { AfterViewInit, Component, OnInit } from '@angular/core';
import { BookService } from '../../services/book/book.service';
import { PageEvent } from '@angular/material';
import { BookDetailsService } from '../../services/book-details/book-details.service';

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

	newBookDTO = {
		'coverPictureUrl': 'https://itbook.store/img/books/9781491985571.png',
		'dateOfPublication': new Date(),
		'description': 'desc',
		'isbn': '12312312312',
		'tableOfContents': 'string',
		'title': 'Book ' + Math.floor(Math.random() * 100)
	};

	constructor(private bookDetailsService: BookDetailsService) {
	}

	ngOnInit() {

		// this.bookDetailsService.saveBook(this.newBookDTO).subscribe((response) => {
		// 	console.log('book added to database');
		// });
		// this.bookDetailsService.updateBook(this.newBookDTO, 1).subscribe((response) => {
		// 	console.log('book updated');
		// });
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
		this.books = await this.bookDetailsService.getBooks(id)
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
