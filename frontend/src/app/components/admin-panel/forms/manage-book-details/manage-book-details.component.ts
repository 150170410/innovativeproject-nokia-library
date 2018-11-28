import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../../../../services/rest/rest.service';
import { BookDetailsDTO } from '../../../../models/database/DTOs/BookDetailsDTO';
import { BookCategory } from '../../../../models/database/entites/BookCategory';
import { MessageInfo } from '../../../../models/MessageInfo';
import { Author } from '../../../../models/database/entites/Author';
import { BookDetails } from '../../../../models/database/entites/BookDetails';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Observable } from 'rxjs';
import { MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MatSort, MatTableDataSource } from '@angular/material';
import { map, startWith } from 'rxjs/operators';

@Component({
	selector: 'app-manage-book-details',
	templateUrl: './manage-book-details.component.html',
	styleUrls: ['./manage-book-details.component.css']
})
export class ManageBookDetailsComponent implements OnInit {

	bookDetailsParams: FormGroup;

	bookCategories: BookCategory[] = [];
	authors: Author[] = [];
	dataSource = new MatTableDataSource<BookDetails>();
	displayedBookDetailColumns: string[] = ['title', 'authors', 'categories', 'coverURL', 'isbn', 'dateOfPublication', 'actions'];

	currentDate = new Date();
	maxDate = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth(), this.currentDate.getDay());

	// variables helpful for mistakes catching
	bookDetailsSubmitted = false;

	// variables for categories form
	selectedCategories: BookCategory[] = [];
	categoryForm = new FormControl();

	// variables foe chip list of authors
	selectable = true;
	removable = true;
	addOnBlur = true;
	separatorKeysCodes: number[] = [ENTER, COMMA];
	authorCtrl = new FormControl();
	filteredAuthors: Observable<string[]>;
	selectedAuthors: any[] = [];
	@ViewChild('authorInput') authorInput: ElementRef<HTMLInputElement>;
	@ViewChild('auto') matAutocomplete: MatAutocomplete;


	constructor(private formBuilder: FormBuilder,
				private http: RestService) {
		this.filteredAuthors = this.authorCtrl.valueChanges.pipe(
			startWith(null),
			map((author: string | null) => author ? this._filter(author) : this.authors.slice()));
	}

	// methods helpful to chip list of authors
	add(event: MatChipInputEvent): void {
		if (!this.matAutocomplete.isOpen) {
			const input = event.input;
			const value = event.value;

			if ((value || '').trim()) {
				this.selectedAuthors.push({
					id: Math.random(),
					authorName: value.trim()
				});
			}

			if (input) {
				input.value = '';
			}

			this.authorCtrl.setValue(null);
		}
	}

	remove(author, index): void {
		if (index >= 0) {
			this.selectedAuthors.splice(index, 1);
		}
	}

	selected(event: MatAutocompleteSelectedEvent): void {
		this.selectedAuthors.push(event.option.value);
		this.authorInput.nativeElement.value = '';
		this.authorCtrl.setValue(null);

		this.filteredAuthors = this.authorCtrl.valueChanges.pipe(
			startWith(null),
			map((author: string | null) => author ? this._filter(author) : this.authors.slice()));
	}

	private _filter(value: any): any[] {
		return this.authors.filter(author => author.authorName.toLowerCase().includes(value.toLowerCase()));
	}

	// end of methods helpful to chip list of authors

	ngOnInit() {
		this.initBookDetailsForm();
		this.getBookDetails();
		this.getCategories();
		this.getAuthors();
		console.log(this.currentDate);
	}

	initBookDetailsForm() {
		this.bookDetailsParams = this.formBuilder.group({
			isbn: ['', [Validators.required, Validators.maxLength(13), Validators.minLength(10)]],
			title: ['', [Validators.required, Validators.maxLength(100)]],
			tableOfContents: ['', Validators.maxLength(100)],
			dateOfPublication: ['', [Validators.required]],
			coverPictureUrl: ['', Validators.maxLength(1000)],
			description: ['', Validators.maxLength(1000)],
		});
	}

	createBookDetails(params: any) {

		this.bookDetailsSubmitted = true;

		if (this.bookDetailsParams.invalid || this.selectedAuthors.length === 0 || this.selectedCategories.length === 0) {
			console.log('mistakes in parameters');
			return;
		}

		const body = new BookDetailsDTO(params.value.coverPictureUrl, params.value.dateOfPublication,
			params.value.description, params.value.isbn,
			params.value.tableOfContents, params.value.title, this.selectedAuthors, this.selectedCategories);
		this.http.save('bookDetails/create', body).subscribe(() => {
			this.getBookDetails();
		});
		this.bookDetailsSubmitted = false;
	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.bookCategories = response.object;
	}

	async getAuthors() {
		const response: MessageInfo = await this.http.getAll('author/getAll');
		this.authors = response.object;
	}

	async getBookDetails() {
		const response: MessageInfo = await this.http.getAll('bookDetails/getAll');
		this.dataSource = new MatTableDataSource(response.object);
	}

	autoFillBookDetailsForm() {
		this.bookDetailsParams.patchValue({
			'coverPictureUrl': 'https://itbook.store/img/books/9781491985571.png',
			'dateOfPublication': new Date(),
			'description': 'desc',
			'isbn': '12312312312',
			'tableOfContents': 'string',
			'title': 'Book ' + Math.floor(Math.random() * 100)
		});
		// TODO: also patchValue for authors and categories
	}

	getInfoFromAPI() {
		// TODO: connect to some book rest API so it autocomplete form when ISBN is given
		console.log('not connected to any API yet');
	}

	editBookDetails(bookDetails: BookDetails) {
		this.bookDetailsParams.patchValue({
			'coverPictureUrl': bookDetails.coverPictureUrl,
			'dateOfPublication': new Date(),
			'description': bookDetails.description,
			'isbn': bookDetails.isbn,
			'tableOfContents': bookDetails.tableOfContents,
			'title': bookDetails.title
		});
		// TODO: also patchValue for authors and categories
	}

	async removeBookDetails(id: number) {
		await this.http.remove('bookDetails/remove/' + `${id}`).subscribe(() => {
			this.getBookDetails();
		});
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
