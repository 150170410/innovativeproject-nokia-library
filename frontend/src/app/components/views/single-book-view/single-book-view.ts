import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageInfo } from '../../../models/MessageInfo';
import { RestService } from '../../../services/rest/rest.service';
import { BookDetails } from '../../../models/database/entites/BookDetails';
import { Book } from '../../../models/database/entites/Book';

@Component({
	selector: 'app-book-details',
	templateUrl: './single-book-view.html',
	styleUrls: ['./single-book-view.css']
})
export class SingleBookViewComponent implements OnInit {

	id: any;
	pageLoading = true;
	bookDetails: BookDetails = new BookDetails();
	booksUnlocked: Book[] = [];

	constructor(private activatedRoute: ActivatedRoute, private http: RestService) {
	}

	ngOnInit() {
		this.activatedRoute.params.subscribe((params) => {
			this.id = params['id'];
		});
		this.getBookDetails();
	}

	async getBookDetails() {
		const response: MessageInfo = await this.http.getAll('bookDetails/getOne/' + this.id);
		this.bookDetails = response.object;
		this.pageLoading = false;
		this.bookDetails.books.forEach((book) =>{
			if(book.status.id !== 5) {
				this.booksUnlocked.push(book);
			}
		});
	}

	actionTaken(val: any) {
		this.getBookDetails();
	}
}
