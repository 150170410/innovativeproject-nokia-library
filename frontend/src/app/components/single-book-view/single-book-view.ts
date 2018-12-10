import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageInfo } from '../../models/MessageInfo';
import { RestService } from '../../services/rest/rest.service';
import { BookDetails } from '../../models/database/entites/BookDetails';

@Component({
	selector: 'app-book-details',
	templateUrl: './single-book-view.html',
	styleUrls: ['./single-book-view.css']
})
export class SingleBookViewComponent implements OnInit {

	id: any;
	pageLoading = true;
	bookDetails: BookDetails = new BookDetails();

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
	}
}
