import { Component, OnInit } from '@angular/core';
import { RestService } from '../../shared/services/rest/rest.service';

@Component({
	selector: 'app-homepage',
	templateUrl: './homepage.component.html',
	styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {


	books: any[];
	fakeBooks = [
		{ 'id': 1, 'title': 'title 1', 'authorName': 'name1', 'authorSurname': 'surname1' },
		{ 'id': 2, 'title': 'title 2', 'authorName': 'name2', 'authorSurname': 'surname2' },
		{ 'id': 3, 'title': 'title 3', 'authorName': 'name2', 'authorSurname': 'surname2' }
	];

	constructor(private restService: RestService) {
	}

	ngOnInit() {

		const url = 'books';
		this.restService.getAll(url).then(response => {
			if (response.status === 200) {
				this.books = response.data._embedded.books;
				console.log(this.books);
			} else {

			}

		}).catch((error) => {
			console.log('error');
			console.log(error);

		});
	}

}
