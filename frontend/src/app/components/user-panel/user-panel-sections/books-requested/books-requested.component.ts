import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { BookToOrder } from '../../../../models/database/entites/BookToOrder';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { RestService } from '../../../../services/rest/rest.service';

@Component({
	selector: 'app-books-requested',
	templateUrl: './books-requested.component.html',
	styleUrls: ['./books-requested.component.css', '../../user-panel.component.css']
})
export class BooksRequestedComponent implements OnInit {
	requestedBooks: BookToOrder[] = [];

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<BookToOrder>();
	displayedColumns: string[] = ['isbn', 'title', 'actions'];

	constructor(private http: RestService,
				private confirmService: ConfirmationDialogService,
				private snackbar: SnackbarService) {
	}

	ngOnInit() {
		this.getRequestedBooks()
	}

	async getRequestedBooks() {
		const response = await this.http.getAll('bookToOrder/getAll');
		this.requestedBooks = response.object;
		this.dataSource = new MatTableDataSource(response.object);
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
	}

	async cancelRequest(request: BookToOrder) {
		await this.confirmService.openDialog('Are you sure you want to cancel this request?').subscribe((result) => {
			if (result) {
				this.http.remove('bookToOrder', request.id).subscribe((response) => {
					if (response.success) {
						this.snackbar.snackSuccess('Request was cancelled successfully!', 'OK');
						this.getRequestedBooks();
					} else {
						this.snackbar.snackError('Error', 'OK');
					}
				}, (error) => {
					this.snackbar.snackError(error.error.message, 'OK');
				});
			}
		})
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
