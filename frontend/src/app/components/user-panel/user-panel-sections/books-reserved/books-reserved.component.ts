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
	styleUrls: ['./books-reserved.component.css', '../../user-panel.component.scss']
})
export class BooksReservedComponent implements OnInit {
	reservations: Reservation[] = [];

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Reservation>();
	displayedColumns: string[] = ['bookTitle', 'reservationDate', 'actions'];

	constructor(private http: RestService,
				private confirmService: ConfirmationDialogService,
				private snackbar: SnackbarService,
				private router: Router) {
	}

	ngOnInit() {
		this.getReservations();
	}

	async getReservations() {
		const response = await this.http.getAll('reservations/user');
		this.reservations = response.object;
		this.dataSource = new MatTableDataSource(response.object);
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
	}

	bookInfo(reservation) {
		const id = reservation.book.bookDetails.id;
		this.router.navigateByUrl('/single-book-view/' + id);
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
