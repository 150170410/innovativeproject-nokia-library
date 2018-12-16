import { AfterViewInit, ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { BookDetailsService } from '../../services/book-details/book-details.service';
import { BookDetails } from '../../models/database/entites/BookDetails';
import { RestService } from '../../services/rest/rest.service';
import { MessageInfo } from '../../models/MessageInfo';

@Component({
	selector: 'app-listview',
	templateUrl: './listview.component.html',
	styleUrls: ['./listview.component.css'],
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListviewComponent implements OnInit, AfterViewInit {

	items = Array.from({length: 100000}).map((_, i) => `Item #${i}`);
	books: any;
	allBooks: BookDetails[] = [];
	errorMessage: any;

	constructor(private bookDetailsService: BookDetailsService, private http: RestService) {
	}

	ngOnInit() {

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
		this.allBooks = response.object.sort().reverse();
	}

}
