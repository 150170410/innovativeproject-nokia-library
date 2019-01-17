import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Book } from '../../models/database/entites/Book';
import { RestService } from '../../services/rest/rest.service';
import { RentalDTO } from '../../models/database/DTOs/RentalDTO';
import { SnackbarService } from '../../services/snackbar/snackbar.service';
import { ReservationDTO } from '../../models/database/DTOs/ReservationDTO';

@Component({
	selector: 'app-book-actions',
	templateUrl: './book-actions.component.html',
	styleUrls: ['./book-actions.component.css']
})
export class BookActionsComponent implements OnInit {

	isAuth: boolean;
	@Input() books: Book[];
	booksUnlocked: Book[] = [];
	@Output() actionTaken = new EventEmitter<boolean>();
	isLoadingActionBorrow = false;
	isLoadingActionReserve = false;
	isLoadingId: number = 0;

	constructor(private http: RestService,
				private snackbar: SnackbarService) {
	}

	ngOnInit() {
		this.isAuth = (sessionStorage.getItem('authenticated') === 'true');
		this.books.forEach((b) => {
			if (b.status.id !== 5) {
				this.booksUnlocked.push(b);
			}
		})
	}


	borrowBook(bookCopy: Book) {
		const body = new RentalDTO(bookCopy.id);
		this.isLoadingId = bookCopy.id;
		this.isLoadingActionBorrow = true;

		this.http.save('rentals/create', body).subscribe((response) => {
			if (response.success) {
				this.snackbar.snackSuccess('Book borrowed successfully!', 'OK');
				const justBorrowed = this.books.findIndex((book: Book) => {
					return book.id == bookCopy.id;
				}, bookCopy);
				let prev: Date = this.books[justBorrowed].availableDate;
				if (prev === null) {
					prev = new Date();
					this.books[justBorrowed].availableDate = prev;
				}
				this.books[justBorrowed].availableDate.setMonth(prev.getMonth() + 1);
				this.books[justBorrowed].status.id = 2;
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
			this.isLoadingId = 0;
			this.isLoadingActionBorrow = false;
		}, (error) => {
			this.isLoadingId = 0;
			this.isLoadingActionBorrow = false;
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	reserveBook(bookCopy: Book) {
		const body = new ReservationDTO(bookCopy.id);
		this.isLoadingId = bookCopy.id;

		this.isLoadingActionReserve = true;
		this.http.save('reservations/create', body).subscribe((response) => {
			if (response.success) {
				this.snackbar.snackSuccess('Book reserved successfully!', 'OK');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
			this.isLoadingId = 0;
			this.isLoadingActionReserve = false;
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
			this.isLoadingId = 0;
			this.isLoadingActionReserve = false;
		});
	}
}
