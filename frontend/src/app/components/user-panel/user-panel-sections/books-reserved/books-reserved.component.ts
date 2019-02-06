import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { RestService } from '../../../../services/rest/rest.service';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';
import { Router } from '@angular/router';
import { Reservation } from '../../../../models/database/entites/Reservation';

@Component({
	selector: 'app-books-reserved',
	templateUrl: './books-reserved.component.html',
	styleUrls: ['./books-reserved.component.scss', '../../user-panel.component.scss']
})
export class BooksReservedComponent implements OnInit {
	reservations: Reservation[] = [];

	isLoadingResults = true;
	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Reservation>();
	displayedColumns: string[] = ['signature', 'bookTitle', 'reservationDate', 'availableDate', 'actions'];

	constructor(private http: RestService,
				private confirmService: ConfirmationDialogService,
				private snackbar: SnackbarService,
				private router: Router) {
	}

	ngOnInit() {
		this.getReservations();
	}

	async getReservations() {
		this.isLoadingResults = true;
		const response = await this.http.getAll('reservations/user');
		this.reservations = response.object;
		this.dataSource = new MatTableDataSource(response.object);
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.isLoadingResults = false;
	}

	bookInfo(reservation) {
		const id = reservation.book.bookDetails.id;
		this.router.navigateByUrl('/single-book-view/' + id);
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	acceptReservation(reservation: Reservation) {
		this.http.save('reservations/accept/' + reservation.id, {}).subscribe((response) => {
			if (response.success) {
				this.snackbar.snackSuccess(response.message, 'OK');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
			this.getReservations();
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	async cancelReservation(reservation: Reservation) {
		await this.confirmService.openDialog('Are you sure you want to cancel this reservation?').subscribe((result) => {
			if (result) {
				this.http.remove('reservations/cancel/', reservation.id).subscribe((response) => {
					if (response.success) {
						this.snackbar.snackSuccess(response.message, 'OK');
					} else {
						this.snackbar.snackError('Error', 'OK');
					}
					this.getReservations();
				}, (error) => {
					this.snackbar.snackError(error.error.message, 'OK');
				});
			}
		});
	}

	async rejectReservation(reservation: Reservation){
		await this.confirmService.openDialog('Are you sure you want to reject this reservation?').subscribe((result) => {
			if (result) {
				this.http.remove('reservations/reject/', reservation.id).subscribe((response) => {
					if (response.success) {
						this.snackbar.snackSuccess(response.message, 'OK');
					} else {
						this.snackbar.snackError('Error', 'OK');
					}
					this.getReservations();
				}, (error) => {
					this.snackbar.snackError(error.error.message, 'OK');
				});
			}
		});
	}
}
