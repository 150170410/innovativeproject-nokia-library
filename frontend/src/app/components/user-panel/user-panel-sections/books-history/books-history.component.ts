import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Rental } from '../../../../models/database/entites/Rental';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';
import { RestService } from '../../../../services/rest/rest.service';
import { Router } from '@angular/router';

@Component({
	selector: 'app-books-history',
	templateUrl: './books-history.component.html',
	styleUrls: ['./books-history.component.scss', '../../user-panel.component.scss']
})
export class BooksHistoryComponent implements OnInit {

	rentals: Rental[] = [];
	rentalsAll: Rental[] = [];

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Rental>();
	displayedColumns: string[] = ['bookTitle', 'rentalDate', 'returnDate', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private http: RestService,
				private confirmService: ConfirmationDialogService,
				private snackbar: SnackbarService,
				private router: Router) {
	}

	ngOnInit() {
		this.getRentals();
	}

	async getRentals() {
		const response = await this.http.getAll('rentals/user');
		this.rentalsAll = response.object;
		this.rentals = this.rentalsAll.filter(r => r.isCurrent === false).map(r => Object.assign({}, r));
		this.dataSource = new MatTableDataSource(this.rentals);
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
	}

	bookInfo(borrowing) {
		const id = borrowing.book.bookDetails.id;
		this.router.navigateByUrl('/single-book-view/' + id);
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

}
