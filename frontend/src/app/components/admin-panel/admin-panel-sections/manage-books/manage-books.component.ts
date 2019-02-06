import { ChangeDetectorRef, Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MessageInfo } from '../../../../models/MessageInfo';
import { RestService } from '../../../../services/rest/rest.service';
import { BookDetails } from '../../../../models/database/entites/BookDetails';
import { HttpClient } from '@angular/common/http';
import { BookDTO } from '../../../../models/database/DTOs/BookDTO';
import { Book } from '../../../../models/database/entites/Book';
import { User } from '../../../../models/database/entites/User';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';
import { MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
	selector: 'app-manage-books',
	templateUrl: './manage-books.component.html',
	styleUrls: ['./manage-books.component.css', '../../admin-panel.component.scss']
})
export class ManageBooksComponent implements OnInit {
	bookCopyParams: FormGroup;
	formMode: string = 'Add';
	selectedBookDetails: BookDetails = null;
	ownersFormControl = new FormControl('');

	toUpdate: Book = null;

	// tables
	@ViewChild('paginatorDetails') paginatorDetails: MatPaginator;
	@ViewChild('paginatorCopies') paginatorCopies: MatPaginator;
	@ViewChild('ownerInput') ownerInput: ElementRef<HTMLInputElement>;
	@ViewChild('autoOwner') autoOwner: MatAutocomplete;
	dataSource = new MatTableDataSource<Book>();
	dataSourceBookDetails = new MatTableDataSource<BookDetails>();

	allOwners: User[] = [];
	availableOwners: string[] = [];
	selectedOwners: string[] = [];
	filteredOwners: Observable<string[]>;
	listOfBookOwners: User[] = [];

	displayedBookCopiesColumns: string[] = ['signature', 'status', 'current_user', 'bookDetails', 'comments', 'actions'];
	displayedBookDetailsColumns: string[] = ['isbn', 'title', 'authors', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				private httpClient: HttpClient,
				private snackbar: SnackbarService,
				private cd: ChangeDetectorRef,
				public confirmService: ConfirmationDialogService) {
					this.filteredOwners = this.ownersFormControl.valueChanges.pipe(
						startWith(null),
						map((owner: string | null) => owner ? this.filterOwner(owner) : this.availableOwners.slice()));
	}

	ngOnInit() {
		this.initBookCopyForm();
		this.getBookCopies();
		this.getBookDetails();
		this.getAdmins();
	}

	initBookCopyForm() {
		this.bookCopyParams = this.formBuilder.group({
			signature: ['', [Validators.required, Validators.maxLength(100)]],
			comments: '',
			owner: this.ownersFormControl
		});
	}

	createBookCopy(params: any) {
		const body = new BookDTO(params.value.signature, this.selectedBookDetails.id, params.value.comments, 1,
			this.ownersToBook());
		console.log(body);
		if (!this.toUpdate) {
			this.http.save('books/create', body).subscribe((response) => {
				if (response.success) {
					this.clearForm();
					this.getBookCopies();
					this.snackbar.snackSuccess(response.message, 'OK');
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
					this.snackbar.snackSuccess(response.message, 'OK');
				} else {
					this.snackbar.snackError('Error', 'OK');
				}
			}, (error) => {
				this.snackbar.snackError(error.error.message, 'OK');
			});
		}
	}

	async getAdmins() {
		const response: MessageInfo = await this.http.getAll('user/admin/getAll');
		this.allOwners = response.object;
		//this.availableOwners = this.ownersToString(this.allOwners).sort();
		this.bookCopyParams.patchValue({ 'owner': '' });
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
		const response: MessageInfo = await this.http.getAll('books/getAllFill');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginatorCopies;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
	}

	editBookCopy(bookCopy: Book) {
		this.bookCopyParams.patchValue({
			'signature': bookCopy.signature,
			'comments': bookCopy.comments
		});
		let takenOwners: User[] = [];
		let freeOwners: User[] = [];
		bookCopy.ownersId.forEach((owner) => {
			this.allOwners.forEach((admin) => {
               if(admin.id == owner.ownerId){
				takenOwners.push(admin);
			   } else {
                freeOwners.push(admin);
			   }
			});
		});
		this.selectedOwners = this.ownersToString(takenOwners);
		this.availableOwners = this.ownersToString(freeOwners);
		this.selectedBookDetails = bookCopy.bookDetails;
		this.toUpdate = bookCopy;
		this.formMode = 'Update';
		document.getElementById('admin-panel-tabs').scrollIntoView();
	}

	async removeBookCopy(id: number) {
		await this.confirmService.openDialog().subscribe((result) => {
			if (result) {
				this.http.remove('books/remove/', id).subscribe((response) => {
					if (response.success) {
						this.snackbar.snackSuccess(response.message, 'OK');
					} else {
						this.snackbar.snackError('Error', 'OK');
					}
					this.getBookCopies();
				}, (error) => {
					this.snackbar.snackError(error.error.message, 'OK');
				});
			}
		});
	}

	clearForm() {
		this.bookCopyParams.reset();
		this.bookCopyParams.markAsPristine();
		this.bookCopyParams.markAsUntouched();
		//this.availableOwners = this.ownersToString(this.allOwners);
		this.availableOwners = [];
		this.selectedOwners = [];
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

	lockBook(bookCopy: Book) {
		const body = {};
		this.http.save('books/lock/' + bookCopy.signature, body).subscribe((response) => {
			if (response.success) {
				this.getBookCopies();
				this.snackbar.snackSuccess(response.message, 'OK');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	unlockBook(bookCopy) {
		const body = {};
		this.http.save('books/unlock/' + bookCopy.signature, body).subscribe((response) => {
			if (response.success) {
				this.getBookCopies();
				this.snackbar.snackSuccess(response.message, 'OK');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}
	removeOwner(owner: string): void {
		const index = this.selectedOwners.indexOf(owner);
	/*	if (index >= 0) {
			if (!this.availableOwners.includes(this.selectedOwners[index])) {
				this.availableOwners.push(this.selectedOwners[index]);
			}*/
			this.selectedOwners.splice(index, 1);
		//}
	}

	selectedOwner(event: MatAutocompleteSelectedEvent): void {
		this.selectedOwners.push(event.option.viewValue);
		this.ownerInput.nativeElement.value = '';
		this.availableOwners = this.availableOwners.filter(e => e !== event.option.viewValue);
		this.ownersFormControl.setValue(null);
	}

	private filterOwner(value: string): string[] {
		return this.availableOwners.filter(owner => owner.toLowerCase().indexOf(value.toLowerCase()) === 0).sort();
	}

	ownersToString(owners: User[]): string[] {
		const arr = [];
		owners.forEach((val) => {
			arr.push(val.firstName + " " + val.lastName);
		});
		return arr;
	}

	ownersToBook() {
		const arr: number[] = [];
		this.selectedOwners.forEach((owner) => {
			this.allOwners.forEach((admin) => {
			  if(admin.firstName == owner.split(" ")[0] && admin.lastName == owner.split(" ")[1])
				arr.push(admin.id);
			});
		});
		return arr;
	}
}
