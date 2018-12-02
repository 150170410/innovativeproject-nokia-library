import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../../../../services/rest/rest.service';
import { BookDetailsDTO } from '../../../../models/database/DTOs/BookDetailsDTO';
import { BookCategory } from '../../../../models/database/entites/BookCategory';
import { MessageInfo } from '../../../../models/MessageInfo';
import { Author } from '../../../../models/database/entites/Author';
import { BookDetails } from '../../../../models/database/entites/BookDetails';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Observable } from 'rxjs';
import { MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MatPaginator, MatTableDataSource } from '@angular/material';
import { map, startWith } from 'rxjs/operators';

@Component({
	selector: 'app-manage-book-details',
	templateUrl: './manage-book-details.component.html',
	styleUrls: ['./manage-book-details.component.css']
})
export class ManageBookDetailsComponent implements OnInit {

	bookDetailsParams: FormGroup;
	authorsFormControl = new FormControl('');
	categoriesFormControl = new FormControl('');

	isUpdating = false;
	toUpdate: BookDetails;

	today = new Date();
	maxDate;

	// mat-chips
	selectable = true;
	removable = true;
	addOnBlur = true;
	separatorKeysCodes: number[] = [ENTER, COMMA];
	@ViewChild('authorInput') authorInput: ElementRef<HTMLInputElement>;
	@ViewChild('autoAth') autoAuthor: MatAutocomplete;
	@ViewChild('categoryInput') categoryInput: ElementRef<HTMLInputElement>;
	@ViewChild('autoCat') autoCategory: MatAutocomplete;

	allCategories: BookCategory[] = [];
	availableCategories: string[] = [];
	selectedCategories: string[] = [];
	filteredCategories: Observable<string[]>;

	allAuthors: Author[] = [];
	availableAuthors: string[] = [];
	selectedAuthors: string[] = [];
	filteredAuthors: Observable<string[]>;

	// table
	@ViewChild(MatPaginator) paginator: MatPaginator;
	dataSource = new MatTableDataSource<BookDetails>();
	displayedBookDetailColumns: string[] = ['title', 'authors', 'categories', 'coverURL', 'isbn', 'dateOfPublication', 'actions'];

	constructor(private formBuilder: FormBuilder,
				private http: RestService) {
		this.filteredAuthors = this.authorsFormControl.valueChanges.pipe(
			startWith(null),
			map((author: string | null) => author ? this.filterAth(author) : this.availableAuthors.slice()));

		this.filteredCategories = this.categoriesFormControl.valueChanges.pipe(
			startWith(''),
			map((category: string | null) => category ? this.filterCat(category) : this.availableCategories.slice()));

	}

	ngOnInit() {
		this.initBookDetailsForm();
		this.getBookDetails();
		this.getCategories();
		this.getAuthors();
		this.maxDate = new Date(this.today.getFullYear(), this.today.getMonth(), this.today.getDay());
	}

	initBookDetailsForm() {
		this.bookDetailsParams = this.formBuilder.group({
			isbn: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(13)]],
			title: ['', [Validators.required, Validators.maxLength(100)]],
			authors: this.authorsFormControl,
			categories: this.categoriesFormControl,
			dateOfPublication: ['', [Validators.required]],
			tableOfContents: ['', Validators.maxLength(100)],
			description: ['', Validators.maxLength(1000)],
			coverPictureUrl: ['', Validators.maxLength(1000)],
		});
	}

	createBookDetails(params: any) {
		const body = new BookDetailsDTO(params.value.isbn,
			params.value.title,
			this.authorsToAuthor(this.selectedAuthors),
			this.categoriesToBookCategory(this.selectedCategories),
			params.value.dateOfPublication,
			params.value.description,
			params.value.tableOfContents,
			params.value.coverPictureUrl
		);
		if (this.isUpdating == false) {

			this.http.save('bookDetails', body).subscribe(() => {
				this.getBookDetails();
			});
		} else {
			this.http.update('bookDetails', this.toUpdate.id, body).subscribe((respone) => {
				this.getBookDetails();
				this.isUpdating = false;
				this.clearForm();
			});
		}
	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.allCategories = response.object;
		this.availableCategories = this.categoriesToString(this.allCategories);
	}

	async getAuthors() {
		const response: MessageInfo = await this.http.getAll('author/getAll');
		this.allAuthors = response.object;
		this.availableAuthors = this.authorsToString(this.allAuthors);
	}

	async getBookDetails() {
		const response: MessageInfo = await this.http.getAll('bookDetails/getAll');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginator;
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
		this.isUpdating = true;
		this.toUpdate = bookDetails;
		this.selectedAuthors = this.authorsToString(bookDetails.authors);
		this.selectedCategories = this.categoriesToString(bookDetails.categories);
	}

	async removeBookDetails(id: number) {
		await this.http.remove('bookDetails', id).subscribe(() => {
			this.getBookDetails();
		});
	}

	clearForm() {
		this.bookDetailsParams.reset();
		this.selectedCategories = [];
		this.selectedAuthors = [];
		this.bookDetailsParams.markAsUntouched();
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
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
		this.selectedAuthors = [this.availableAuthors[0]];
		this.selectedCategories = [this.availableCategories[0]];
	}

	// authors chips
	addAth(event: MatChipInputEvent): void {
		if (!this.autoAuthor.isOpen) {
			const input = event.input;
			const value = event.value;

			if ((value || '').trim()) {
				this.selectedAuthors.push(value.trim());
			}

			if (input) {
				input.value = '';
			}

			this.authorsFormControl.setValue(null);
		}
	}

	removeAth(author: string): void {
		const index = this.selectedAuthors.indexOf(author);
		if (index >= 0) {
			this.selectedAuthors.splice(index, 1);
		}
	}

	selectedAth(event: MatAutocompleteSelectedEvent): void {
		this.selectedAuthors.push(event.option.viewValue);
		this.authorInput.nativeElement.value = '';

		this.availableAuthors = this.availableAuthors.filter(e => e !== event.option.viewValue);
		this.authorsFormControl.setValue(null);
	}

	private filterAth(value: string): string[] {
		return this.availableAuthors.filter(author => author.toLowerCase().indexOf(value.toLowerCase()) === 0);
	}

	authorsToString(authors: Author[]) {
		const arr = [];
		authors.forEach((val) => {
			arr.push(val.authorName + ' ' + val.authorSurname);
		});
		return arr;
	}

	authorsToAuthor(authors: string[]) {
		const arr: Author[] = [];
		authors.forEach((val) => {
			const ath = val.split(' ');
			arr.push(this.allAuthors.filter(e => e.authorName === ath[0] && e.authorSurname === ath[1])[0]);
		});
		return arr;
	}

	// categories chips
	addCat(event: MatChipInputEvent): void {
		if (!this.autoCategory.isOpen) {
			const input = event.input;
			const value = event.value;

			if ((value || '').trim()) {
				this.selectedCategories.push(value.trim());
			}

			if (input) {
				input.value = '';
			}

			this.categoriesFormControl.setValue(null);
		}
	}

	removeCat(category: string): void {
		const index = this.selectedCategories.indexOf(category);
		if (index >= 0) {
			this.selectedCategories.splice(index, 1);
		}
	}

	selectedCat(event: MatAutocompleteSelectedEvent): void {
		this.selectedCategories.push(event.option.viewValue);
		this.categoryInput.nativeElement.value = '';
		this.availableCategories = this.availableCategories.filter(e => e !== event.option.viewValue);
		this.categoriesFormControl.setValue(null);
	}

	private filterCat(value: string): string[] {
		return this.availableCategories.filter(category => category.toLowerCase().indexOf(value.toLowerCase()) === 0);
	}

	categoriesToString(categories: BookCategory[]) {
		const arr = [];
		categories.forEach((val) => {
			arr.push(val.bookCategoryName);
		});
		return arr;
	}

	categoriesToBookCategory(categories: string[]) {
		const arr: BookCategory[] = [];
		categories.forEach((val) => {
			arr.push(this.allCategories.filter(e => e.bookCategoryName == val)[0]);
		});
		return arr;
	}
}
