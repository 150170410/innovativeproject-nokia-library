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
	@Output() actionTaken = new EventEmitter<boolean>();

	constructor(private http: RestService,
				private snackbar: SnackbarService) {
	}

	ngOnInit() {
		this.isAuth = (sessionStorage.getItem('authenticated') === 'true');
	}


	borrowBook(bookCopy: Book) {
		const body = new RentalDTO(bookCopy.id);
		this.http.save('rentals', body).subscribe((response) => {
			if (response.success) {
				this.snackbar.snackSuccess('Book borrowed successfully!', 'OK');
				const justBorrowed = this.books.findIndex((book: Book) => {
					return book.id == bookCopy.id;
				}, bookCopy);
				let now: Date = new Date();
				this.books[justBorrowed].availableDate = now;
				this.books[justBorrowed].availableDate.setMonth(now.getMonth() + 1);
				this.books[justBorrowed].status.id = 2;
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	reserveBook(bookCopy: Book) {
		const body = new ReservationDTO(bookCopy.id);
		this.http.save('reservations', body).subscribe((response) => {
			if (response.success) {
				this.snackbar.snackSuccess('Book reserved successfully!', 'OK');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}
}
