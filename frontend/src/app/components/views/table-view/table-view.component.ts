import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MessageInfo } from '../../../models/MessageInfo';
import { RestService } from '../../../services/rest/rest.service';
import { BookDetailsService } from '../../../services/book-details/book-details.service';
import { BookDetails } from '../../../models/database/entites/BookDetails';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { Book } from '../../../models/database/entites/Book';

@Component({
	selector: 'app-table-view',
	templateUrl: './table-view.component.html',
	styleUrls: ['./table-view.component.css'],
	animations: [
		trigger('detailExpand', [
			state('collapsed', style({ height: '0px', minHeight: '0', display: 'none' })),
			state('expanded', style({ height: '*' })),
			transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
		]),
	],
})
export class TableViewComponent implements OnInit {

	booksAll: BookDetails[] = [];
	books: BookDetails[] = [];
	listIsLoading = false;
	hideUnavailable = false;
	value = '';

	// table
	@ViewChild(MatPaginator) paginator: MatPaginator;
	dataSource = new MatTableDataSource<BookDetails>();
	displayedBookDetailColumns: string[] = ['title', 'authors', 'categories', 'availableBooks'];
	@ViewChild(MatSort) sort: MatSort;

	expandedElement = null;

	constructor(private bookDetailsService: BookDetailsService, private http: RestService) {
	}

	ngOnInit() {
		this.getBooksDetails();
	}

	searchBooks(val) {
		// if (this.hideUnavailable) {
		// 	this.books = this.booksAll.filter(b => JSON.stringify(b).toLowerCase().includes(val.toLowerCase()))
		// } else {
		// 	this.books = this.booksAll.filter(b => JSON.stringify(b).toLowerCase().includes(val.toLowerCase()) && (b.books.length > 0))
		// }

		// .map(b => Object.assign({}, b));
		this.dataSource.filter = val.trim().toLowerCase();
	}

	async getBooksDetails() {
		this.listIsLoading = true;
		const response: MessageInfo = await this.http.getAll('bookDetails/getAll');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
		this.listIsLoading = false;
	}
}
