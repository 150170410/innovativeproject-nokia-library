import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { RestService } from '../../../../services/rest/rest.service';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';
import { Rental } from '../../../../models/database/entites/Rental';
import { Router } from '@angular/router';

@Component({
	selector: 'app-books-borrowed',
	templateUrl: './books-borrowed.component.html',
	styleUrls: ['./books-borrowed.component.scss', '../../user-panel.component.scss']
})
export class BooksBorrowedComponent implements OnInit {

	rentals: Rental[] = [];
	rentalsAll: Rental[] = [];
	canProlongDate = new Date();

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Rental>();
	displayedColumns: string[] = ['signature', 'bookTitle', 'status', 'rentalDate', 'returnDate', 'goTo', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	resultsLength = 0;
	isLoadingResults = true;
	isRateLimitReached = false;

	constructor(private http: RestService,
				private confirmService: ConfirmationDialogService,
				private snackbar: SnackbarService,
				private router: Router) {
	}

	ngOnInit() {
		this.getRentals();
		this.canProlongDate = new Date();
		this.canProlongDate.setDate(this.canProlongDate.getDate() + 21);
	}

	async cancelAwaiting(rental: Rental) {
		await this.confirmService.openDialog('Are you sure you want to cancel?').subscribe((result) => {
			if (result) {
				this.http.remove('rentals/remove/', rental.id).subscribe((response) => {
					if (response.success) {
						this.snackbar.snackSuccess(response.message, 'OK');
					} else {
						this.snackbar.snackError('Error', 'OK');
					}
					this.getRentals();
				}, (error) => {
					this.snackbar.snackError(error.error.message, 'OK');
				});
			}
		})
	}

	prolong(rental: Rental) {
		const body = {};
		this.http.save('rentals/prolong/' + rental.id, body).subscribe((response) => {
			if (response.success) {
				this.snackbar.snackSuccess(response.message, 'OK');
				this.getRentals();
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	async getRentals() {
		const response = await this.http.getAll('rentals/user');
		this.rentalsAll = response.object;
		this.rentals = this.rentalsAll.filter(r => r.isCurrent).map(r => Object.assign({}, r));
		this.isLoadingResults = false;
		this.dataSource = new MatTableDataSource(this.rentals);
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
		for (let i = 0; i < this.rentals.length; i++) {
			this.rentals[i].returnDate = new Date(this.rentals[i].returnDate);
		}

	}

	bookInfo(borrowing) {
		const id = borrowing.book.bookDetails.id;
		this.router.navigateByUrl('/single-book-view/' + id);
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
