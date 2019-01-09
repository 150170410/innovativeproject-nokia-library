import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageInfo } from '../../../../models/MessageInfo';
import { RestService } from '../../../../services/rest/rest.service';
import { BookDetails } from '../../../../models/database/entites/BookDetails';
import { HttpClient } from '@angular/common/http';
import { BookDTO } from '../../../../models/database/DTOs/BookDTO';
import { Book } from '../../../../models/database/entites/Book';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';

@Component({
	selector: 'app-manage-books',
	templateUrl: './manage-books.component.html',
	styleUrls: ['./manage-books.component.css', '../../admin-panel.component.css']
})
export class ManageBooksComponent implements OnInit {
	bookCopyParams: FormGroup;
	formMode: string = 'Add';
	selectedBookDetails: BookDetails = null;

	toUpdate: Book = null;

	// tables
	@ViewChild('paginatorDetails') paginatorDetails: MatPaginator;
	@ViewChild('paginatorCopies') paginatorCopies: MatPaginator;
	dataSource = new MatTableDataSource<Book>();
	dataSourceBookDetails = new MatTableDataSource<BookDetails>();

	displayedBookCopiesColumns: string[] = ['signature', 'status', 'bookDetails', 'comments', 'actions'];
	displayedBookDetailsColumns: string[] = ['isbn', 'title', 'authors', 'actions'];

	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				private httpClient: HttpClient,
				private snackbar: SnackbarService,
				private cd: ChangeDetectorRef,
				public confirmService: ConfirmationDialogService) {
	}

	ngOnInit() {
		this.initBookCopyForm();
		this.getBookCopies();
		this.getBookDetails();
	}

	initBookCopyForm() {
		this.bookCopyParams = this.formBuilder.group({
			signature: ['', [Validators.required, Validators.maxLength(100)]],
			comments: ''
		});
	}

	createBookCopy(params: any) {
		const body = new BookDTO(params.value.signature, this.selectedBookDetails.id, params.value.comments, 1);
		console.log(body);
		if (!this.toUpdate) {
			this.http.save('books', body).subscribe((response) => {
				if (response.success) {
					this.clearForm();
					this.getBookCopies();
					this.snackbar.snackSuccess('Book details added successfully!', 'OK');
				} else {
					this.snackbar.snackError('Error', 'OK');
				}
			}, (error) => {
				this.snackbar.snackError(error.error.message, 'OK');
			});
		} else {
			this.http.update('books', this.toUpdate.id, body).subscribe((response) => {
				if (response.success) {
					this.toUpdate = null;
					this.clearForm();
					this.getBookCopies();
					this.formMode = 'Add';
					this.snackbar.snackSuccess('Book copy updated successfully!', 'OK');
				} else {
					this.snackbar.snackError('Error', 'OK');
				}
			}, (error) => {
				this.snackbar.snackError(error.error.message, 'OK');
			});
		}
	}

	async getBookDetails() {
		const response: MessageInfo = await this.http.getAll('bookDetails/getAll');
		this.dataSourceBookDetails = new MatTableDataSource(response.object.reverse());
		this.dataSourceBookDetails.paginator = this.paginatorDetails;
		this.dataSourceBookDetails.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
	}

	async getBookCopies() {
		const response: MessageInfo = await this.http.getAll('books/getAll');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginatorCopies;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
	}

	editBookCopy(bookCopy: Book) {
		this.bookCopyParams.patchValue({
			'signature': bookCopy.signature,
			'comments': bookCopy.comments
		});
		this.selectedBookDetails = bookCopy.bookDetails;
		this.toUpdate = bookCopy;
		this.formMode = 'Update';
		document.getElementById('admin-panel-tabs').scrollIntoView();
	}

	async removeBookCopy(id: number) {
		await this.confirmService.openDialog().subscribe((result) => {
			if (result) {
				this.http.remove('books', id).subscribe((response) => {
					if (response.success) {
						this.snackbar.snackSuccess('Book copy removed successfully!', 'OK');
					} else {
						this.snackbar.snackError('Error', 'OK');
					}
					this.getBookCopies();
				}, (error) => {
					this.snackbar.snackError(error.error.message, 'OK');
				});
			}
		})
	}

	clearForm() {
		this.bookCopyParams.reset();
		this.bookCopyParams.markAsPristine();
		this.bookCopyParams.markAsUntouched();

		this.selectedBookDetails = null;
		this.cd.markForCheck();
	}

	selectBookDetails(bookDetails) {
		this.selectedBookDetails = bookDetails;
	}

	applyFilterBookDetails(filterValue: string) {
		this.dataSourceBookDetails.filter = filterValue.trim().toLowerCase();
	}

	applyFilterBooks(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	cancelUpdate() {
		this.toUpdate = null;
		this.clearForm();
	}

	lockBook(bookCopy) {

	}

	unlockBook(bookCopy) {

	}
}
