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
	newBook = { 'id': null, 'title': 'book ' + Math.floor(Math.random() * 100), 'authorName': 'name ' + Math.floor(Math.random() * 100), 'authorSurname': 'surname' + Math.floor(Math.random() * 100) };

	constructor(private restService: RestService) {
	}

	ngOnInit() {

		const url = 'library/books';
		// get all books
		this.restService.getAll('library/books').then(response => {
			if (response.status === 200) {
				this.books = response.data;
				console.log(response.data);
			} else {

			}

		}).catch((error) => {
			console.log('error');
			console.log(error);

		});

		// post
		this.restService.post("books", this.newBook)
		.then(function (response) {
			if (response.status == 201) {
				console.log('book added')
			} else {
				console.log('error');
			}
		}).catch((error) => {
			console.log('error');
		});

		// delete
		// this.restService.remove('books', 71).then(response => {
		// 	console.log('book removed');
		// })

	}

}
