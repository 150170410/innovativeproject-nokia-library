import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { MessageInfo } from '../../../../models/MessageInfo';
import { Book } from '../../../../models/database/entites/Book';
import { RestService } from '../../../../services/rest/rest.service';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { Rental } from '../../../../models/database/entites/Rental';

@Component({
	selector: 'app-manage-handovers',
	templateUrl: './manage-handovers.component.html',
	styleUrls: ['./manage-handovers.component.css', '../../admin-panel.component.scss']
})
export class ManageHandoversComponent implements OnInit {

	rentals: Rental[] = [];

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Book>();
	displayedColumns: string[] = ['signature', 'current_user', 'status', 'bookDetails', 'comments', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private http: RestService,
				private snackbar: SnackbarService) {
	}

	ngOnInit() {
		this.getRentals();
		this.getBookCopies();
	}

	handoverBook(book: Book) {
		let body = {};
		let id = 1;
		for (let i = 0; i < this.rentals.length; i++) {
			if (this.rentals[i].book.id == book.id) {
				id = this.rentals[i].id;
			}
		}
		this.http.save('rentals/handover/' + id, body).subscribe((response) => {
			if (response.success) {
				this.getBookCopies();
				this.snackbar.snackSuccess('Book handover successful!', 'OK');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	async getBookCopies() {
		const response: MessageInfo = await this.http.getAll('books/getAll');
		let borrowedBooks = [];
		for (let i = 0; i < response.object.length; i++) {
			if (response.object[i].status.id === 2) {
				borrowedBooks.push(response.object[i])
			}
		}
		this.dataSource = new MatTableDataSource(borrowedBooks.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
	}

	async getRentals() {
		const response: MessageInfo = await this.http.getAll('rentals/getAll');
		this.rentals = response.object;
		console.log(this.rentals);
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
