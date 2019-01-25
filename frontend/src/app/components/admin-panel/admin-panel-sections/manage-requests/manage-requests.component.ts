import { Component, OnInit, ViewChild } from '@angular/core';
import { RestService } from '../../../../services/rest/rest.service';
import { BookToOrder } from '../../../../models/database/entites/BookToOrder';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';

@Component({
	selector: 'app-manage-requests',
	templateUrl: './manage-requests.component.html',
	styleUrls: ['./manage-requests.component.css', '../../admin-panel.component.scss']
})
export class ManageRequestsComponent implements OnInit {

	requestedBooks: BookToOrder[] = [];

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<BookToOrder>();
	displayedColumns: string[] = ['isbn', 'title', 'totalSubs', 'actions'];

	constructor(private http: RestService,
				private confirmService: ConfirmationDialogService,
				private snackbar: SnackbarService) {
	}

	ngOnInit() {
		this.getRequestedBooks()
	}

	async fulfillRequest(request: BookToOrder) {
		await this.http.remove('bookToOrder', request.id).subscribe((response) => {
			if (response.success) {
				this.snackbar.snackSuccess('Thank you for fulfilling the request!', 'OK');
				this.getRequestedBooks();
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});

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

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	rejectRequest(request) {

	}
}
