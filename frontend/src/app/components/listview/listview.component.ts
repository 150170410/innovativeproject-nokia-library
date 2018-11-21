import { AfterViewInit, Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material';
import { BookDetailsService } from '../../services/book-details/book-details.service';
import { BookDetails } from '../../models/database/entites/BookDetails';
import { RestService } from '../../services/rest/rest.service';
import { MessageInfo } from '../../models/MessageInfo';

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
	allBooks: BookDetails[] = [];
	errorMessage: any;

	newBookDTO = {
		'coverPictureUrl': 'https://itbook.store/img/books/9781491985571.png',
		'dateOfPublication': new Date(),
		'description': 'desc',
		'isbn': '12312312312',
		'tableOfContents': 'string',
		'title': 'Book ' + Math.floor(Math.random() * 100)
	};

	constructor(private bookDetailsService: BookDetailsService, private http: RestService) {
	}

	ngOnInit() {
		this.pageEvent.pageIndex = 0;
		this.pageEvent.pageSize = 10;

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

		const response: MessageInfo = await this.http.getAll('bookDetails/getAll');
		this.allBooks = response.object;

		console.log(this.books);
		console.log(this.allBooks);
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
