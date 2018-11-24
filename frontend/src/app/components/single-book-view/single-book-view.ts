import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageInfo } from '../../models/MessageInfo';
import { RestService } from '../../services/rest/rest.service';
import { BookDetails } from '../../models/database/entites/BookDetails';
import { Author } from '../../models/database/entites/Author';
import { Review } from '../../models/database/entites/Review';
import { BookCategory } from '../../models/database/entites/BookCategory';

@Component({
	selector: 'app-book-details',
	templateUrl: './single-book-view.html',
	styleUrls: ['./single-book-view.css']
})
export class SingleBookViewComponent implements OnInit {

	id: any;
	bookDetails: BookDetails = new BookDetails();
	authors: Author[] = [];
	reviews: Review[] = [];
	bookCopies: Review[] = [];
	categories: BookCategory[] = [];

	constructor(private activatedRoute: ActivatedRoute, private http: RestService) {
	}

	ngOnInit() {
		this.activatedRoute.params.subscribe((params) => {
			this.id = params['id'];
		});

		this.getBookDetails();

		console.log(this.id);

	}

	async getBookDetails() {
		const response: MessageInfo = await this.http.getAll('bookDetails/getOne/' + this.id);
		this.bookDetails = response.object;
	}
}
