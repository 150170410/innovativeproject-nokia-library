import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { BookToOrder } from '../../../../models/database/entites/BookToOrder';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { RestService } from '../../../../services/rest/rest.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookToOrderDTO } from '../../../../models/database/DTOs/BookToOrderDTO';
import { MessageInfo } from '../../../../models/MessageInfo';
import { IsbnValidationService } from 'src/app/services/isbn-validation/isbn-validation.service';

@Component({
	selector: 'app-books-requested',
	templateUrl: './books-requested.component.html',
	styleUrls: ['./books-requested.component.css', '../../user-panel.component.scss']
})
export class BooksRequestedComponent implements OnInit {

	requestedBooks: BookToOrder[] = [];
	requestParams: FormGroup;

	isLoadingResults = true;
	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<BookToOrder>();
	displayedColumns: string[] = ['isbn', 'totalSubs', 'title', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				private confirmService: ConfirmationDialogService,
				private snackbar: SnackbarService,
				private isbnValidation: IsbnValidationService) {
	}

	ngOnInit() {
		this.getRequestedBooks()
		this.initRequestParams();
	}

	initRequestParams() {
		this.requestParams = this.formBuilder.group({
			isbn: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(17), Validators.pattern('(([0-9Xx][- ]*){13}|([0-9Xx][- ]*){10})')]],
			title: ['', [Validators.required, Validators.maxLength(100)]],
		});
	}

	async getRequestedBooks() {
		this.isLoadingResults = true;
		const response = await this.http.getAll('bookToOrder/getAll');
		this.requestedBooks = response.object;
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
		this.isLoadingResults = false;
	}

	async cancelRequest(request: BookToOrder) {
		await this.confirmService.openDialog('Are you sure you want to cancel this request?').subscribe((result) => {
			if (result) {
				this.http.remove('bookToOrder/remove/', request.id).subscribe((response) => {
					if (response.success) {
						this.snackbar.snackSuccess(response.message, 'OK');
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

	requestBook(requestParams: FormGroup) {
		if(this.isbnValidation.validateISBN(this.requestParams.value.isbn)){
			const body = new BookToOrderDTO(requestParams.value.isbn, requestParams.value.title);
			this.http.save('bookToOrder/create', body).subscribe((response: MessageInfo) => {
				if (response.success) {
					this.clearForm();
					this.getRequestedBooks();
					this.snackbar.snackSuccess('Book requested successfully!', 'OK');
				} else {
					this.snackbar.snackError('Error', 'OK');
				}
			}, (error) => {
				this.snackbar.snackError(error.error.message, 'OK');
			});
		} else {
			this.snackbar.snackError('This is not real ISBN', 'OK');
		}
	}
	clearForm() {
		this.requestParams.reset();
		this.requestParams.markAsPristine();
		this.requestParams.markAsUntouched();
	}

	changeSubscribeState(request) {
		this.http.changeSubscribeStatus(request.id)
		.subscribe(
			(response: MessageInfo) => {
				if (response.success) {
					this.requestedBooks.forEach(element => {
						if(element.id == request.id){
							element.subscribed = !element.subscribed;
							element.subscribed ? element.totalSubs++ : element.totalSubs--;
						}

					});
					this.snackbar.snackSuccess('Success', 'OK');
				}
			}, (error) => {
				this.snackbar.snackError(error.error.message, 'OK');
		});
	}
}
