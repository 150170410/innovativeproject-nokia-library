import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {MessageInfo} from '../../models/entites/MessageInfo';
import {RestService} from '../../services/rest/rest.service';
import {BookDetails} from '../../models/entites/BookDetails';
import {Author} from '../../models/entites/Author';
import {Review} from '../../models/entites/Review';
import {BookCategory} from '../../models/entites/BookCategory';

@Component({
	selector: 'app-book-details',
	templateUrl: './single-book-view.html',
	styleUrls: ['./single-book-view.css']
})
export class SingleBookViewComponent implements OnInit {

	id: any;
	bookDetails: BookDetails;
	authors: Author[] = [];
	reviews: Review[] = [];
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
    this.authors = this.bookDetails.authors;
    this.reviews = this.bookDetails.reviews;
    this.categories = this.bookDetails.categories;

    console.log(this.bookDetails);
    console.log(this.authors);
    console.log(this.reviews);
    console.log(this.categories);
  }
}
