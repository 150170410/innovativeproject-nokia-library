import { Component, OnInit } from '@angular/core';
import { BookDetailsService } from '../../../services/book-details/book-details.service';
import { BookDetails } from '../../../models/database/entites/BookDetails';
import { RestService } from '../../../services/rest/rest.service';
import { MessageInfo } from '../../../models/MessageInfo';

@Component({
	selector: 'app-listview',
	templateUrl: './listview.component.html',
	styleUrls: ['./listview.component.css']
})
export class ListviewComponent implements OnInit {

	booksAll: BookDetails[] = [];
	books: BookDetails[] = [];
	listIsLoading = false;
	hideUnavailable = false;
	value = '';

	constructor(private bookDetailsService: BookDetailsService, private http: RestService) {
	}

	ngOnInit() {
		this.getBooksDetails();
	}

	searchBooks(val) {
		if (this.hideUnavailable) {
			this.books = this.booksAll.filter(b => JSON.stringify(b).toLowerCase().includes(val.toLowerCase()))
		} else {
			this.books = this.booksAll.filter(b => JSON.stringify(b).toLowerCase().includes(val.toLowerCase()) && (b.books.length > 0))
		}

		// .map(b => Object.assign({}, b));
	}

	async getBooksDetails() {
		this.listIsLoading = true;
		const response: MessageInfo = await this.http.getAll('bookDetails/getAll');
		this.booksAll = response.object.sort().reverse();
		this.listIsLoading = false;
		this.books = this.booksAll;
	}

}
