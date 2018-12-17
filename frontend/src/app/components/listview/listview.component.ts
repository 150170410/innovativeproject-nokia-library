import { AfterViewInit, Component, OnInit } from '@angular/core';
import { BookDetailsService } from '../../services/book-details/book-details.service';
import { BookDetails } from '../../models/database/entites/BookDetails';
import { RestService } from '../../services/rest/rest.service';
import { MessageInfo } from '../../models/MessageInfo';

@Component({
	selector: 'app-listview',
	templateUrl: './listview.component.html',
	styleUrls: ['./listview.component.css']
})
export class ListviewComponent implements OnInit {

	books: any;
	allBooks: BookDetails[] = [];
	errorMessage: any;

	constructor(private bookDetailsService: BookDetailsService, private http: RestService) {
	}

	ngOnInit() {
		this.getBooksDetails();
	}

	async getBooksDetails() {
		const response: MessageInfo = await this.http.getAll('bookDetails/getAll');
		this.allBooks = response.object.sort().reverse();
	}

}
