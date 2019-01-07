import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { RestService } from '../../../../services/rest/rest.service';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';
import { Rental } from '../../../../models/database/entites/Rental';
import { Router } from '@angular/router';

@Component({
	selector: 'app-books-borrowed',
	templateUrl: './books-borrowed.component.html',
	styleUrls: ['./books-borrowed.component.css', '../../user-panel.component.css']
})
export class BooksBorrowedComponent implements OnInit {

	borrowings: Rental[] = [];

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Rental>();
	displayedColumns: string[] = ['bookTitle', 'rentalDate', 'returnDate', 'handOverDate', 'actions'];

	constructor(private http: RestService,
				private confirmService: ConfirmationDialogService,
				private snackbar: SnackbarService,
				private router: Router) {
	}

	ngOnInit() {
		this.getBorrowedBooks();
	}

	async getBorrowedBooks() {
		const response = await this.http.getAll('rentals/getAll');
		this.borrowings = response.object;
		this.dataSource = new MatTableDataSource(response.object);
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
	}

	bookInfo(borrowing) {
		const id = borrowing.book.bookDetails.id;
		this.router.navigateByUrl('/single-book-view/' + id);
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
