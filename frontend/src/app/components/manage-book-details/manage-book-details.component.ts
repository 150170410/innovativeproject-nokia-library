import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../../services/rest/rest.service';
import { BookCategoryDTO } from '../../models/database/DTOs/BookCategoryDTO';
import { AuthorDTO } from '../../models/database/DTOs/Author';
import { BookDetailsDTO } from '../../models/database/DTOs/BookDetailsDTO';
import { BookCategory } from '../../models/database/entites/BookCategory';
import { MessageInfo } from '../../models/MessageInfo';
import { Author } from '../../models/database/entites/Author';
import { BookDetails } from '../../models/database/entites/BookDetails';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Observable } from 'rxjs';
import { MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent } from '@angular/material';
import { map, startWith } from 'rxjs/operators';

@Component({
	selector: 'app-manage-book-details',
	templateUrl: './manage-book-details.component.html',
	styleUrls: ['./manage-book-details.component.css']
})
export class ManageBookDetailsComponent implements OnInit {

	categoryParams: FormGroup;
	authorParams: FormGroup;
	bookDetailsParams: FormGroup;


	bookCategories: BookCategory[] = [];
	displayedCategoryColumns: string[] = ['id', 'bookCategoryName'];
	authors: Author[] = [];
	displayedAuthorsColumn: string[] = ['id', 'authorName', 'authorSurname'];
	booksDetails: BookDetails[] = [];
	displayedBookDetailColumns: string[] = ['id', 'title', 'dateOfPublication', 'isbn'];

	// variable for date validation
	currentDate = new Date();
	currentYear = this.currentDate.getFullYear();
	currentMonth = this.currentDate.getMonth();
	currentDay = this.currentDate.getDay();

	maxDate = new Date(this.currentYear, this.currentMonth, this.currentDay);


	// variables helpful for mistakes catching
	categorySubmitted = false;
	authorSubmitted = false;
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
	myAuthors: any[] = [];
	@ViewChild('authorInput') authorInput: ElementRef<HTMLInputElement>;
	@ViewChild('auto') matAutocomplete: MatAutocomplete;


	constructor(private formBuilder: FormBuilder, private http: RestService) {
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
				this.myAuthors.push({
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
			this.myAuthors.splice(index, 1);
		}
	}

	selected(event: MatAutocompleteSelectedEvent): void {
		this.myAuthors.push(event.option.value);
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
		this.initCategoriesForm();
		this.initAuthorForm();
		this.initBookDetailsForm();
		console.log(this.currentDate);
	}

	initCategoriesForm() {
		this.categoryParams = this.formBuilder.group({
			categoryName: ['', [Validators.required, Validators.maxLength(50)]]
		});
		this.getCategories();
	}

	initAuthorForm() {
		this.authorParams = this.formBuilder.group({
			authorName: ['', [Validators.required, Validators.maxLength(300)]],
			authorSurname: ['', [Validators.required, Validators.maxLength(300)]],
			authorDescription: ['', Validators.maxLength(10000)]
		});
		this.getAuthors();
	}

	initBookDetailsForm() {
		this.bookDetailsParams = this.formBuilder.group({
			coverPictureUrl: ['', Validators.maxLength(1000)],
			dateOfPublication: ['', [Validators.required]],
			description: ['', Validators.maxLength(1000)],
			isbn: ['', [Validators.required, Validators.maxLength(13), Validators.minLength(10)]],
			tableOfContents: ['', Validators.maxLength(100)],
			title: ['', [Validators.required, Validators.maxLength(100)]]
		});
		this.getBookDetails();
	}

	createCategory(params: any) {

		this.categorySubmitted = true;

		if (this.categoryParams.invalid) {
			console.log('mistakes in parameters');
			return;
		}

		const body = new BookCategoryDTO(params.value.categoryName);
		this.http.save('bookCategory/create', body).subscribe(() => {
			console.log('category created');
			this.getCategories();
		});

		// MUST BE DELETED AFTER BACKEND VALIDATION
		this.getCategories();
		console.log(this.bookCategories);
		// MUST BE DELETED AFTER BACKEND VALIDATION

		this.categorySubmitted = false;
	}

	createAuthor(params: any) {

		this.authorSubmitted = true;

		if (this.authorParams.invalid) {
			console.log('mistakes in parameters');
			return;
		}

		const body = new AuthorDTO(params.value.authorName, params.value.authorSurname, params.value.authorDescription);
		console.log(body);
		this.http.save('author/create', body).subscribe(() => {
			console.log('author created');
		});

		// MUST BE DELETED AFTER BACKEND VALIDATION
		this.getAuthors();
		// MUST BE DELETED AFTER BACKEND VALIDATION

		this.authorSubmitted = false;
	}

	createBookDetails(params: any) {

		this.bookDetailsSubmitted = true;

		if (this.bookDetailsParams.invalid || this.myAuthors.length === 0 || this.selectedCategories.length === 0) {
			console.log('mistakes in parameters');
			return;
		}

		const body = new BookDetailsDTO(params.value.coverPictureUrl, params.value.dateOfPublication,
			params.value.description, params.value.isbn,
			params.value.tableOfContents, params.value.title, this.myAuthors, this.selectedCategories);
		this.http.save('bookDetails/create', body).subscribe(() => {
			console.log('book details created');
		});

		// MUST BE DELETED AFTER BACKEND VALIDATION
		this.getBookDetails();
		// MUST BE DELETED AFTER BACKEND VALIDATION

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
		this.booksDetails = response.object;
	}

	autoFillAuthorForm() {
		this.authorParams.patchValue(new AuthorDTO('JRR', 'Tolkien', 'frodo and shit'));
	}

	autoFillBookDetialsForm() {
		const sampleBookDTO = {
			'coverPictureUrl': 'https://itbook.store/img/books/9781491985571.png',
			'dateOfPublication': new Date(),
			'description': 'desc',
			'isbn': '12312312312',
			'tableOfContents': 'string',
			'title': 'Book ' + Math.floor(Math.random() * 100)
		};
		this.bookDetailsParams.patchValue(sampleBookDTO);
	}

	getInfoFromAPI() {
		// TODO: connect to some book rest API so it autocomplete form when ISBN is given
		console.log('not connected to any API yet');
	}
}
