import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { RestService } from '../../../../services/rest/rest.service';
import { BookDetailsDTO } from '../../../../models/database/DTOs/BookDetailsDTO';
import { BookCategory } from '../../../../models/database/entites/BookCategory';
import { MessageInfo } from '../../../../models/MessageInfo';
import { Author } from '../../../../models/database/entites/Author';
import { BookDetails } from '../../../../models/database/entites/BookDetails';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Observable } from 'rxjs';
import { MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MatPaginator, MatSnackBar, MatTableDataSource } from '@angular/material';
import { map, startWith } from 'rxjs/operators';
import { AuthorDTO } from '../../../../models/database/DTOs/AuthorDTO';
import { API_URL } from '../../../../config';

@Component({
	selector: 'app-manage-book-details',
	templateUrl: './manage-book-details.component.html',
	styleUrls: ['./manage-book-details.component.css']
})
export class ManageBookDetailsComponent implements OnInit {

	bookDetailsParams: FormGroup;
	authorsFormControl = new FormControl('');
	categoriesFormControl = new FormControl('');

	toUpdate: BookDetails = null;
	uploadingFile = false;
	today = new Date();
	maxDate;
	fileToUpload: File = null;

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
	displayedBookDetailColumns: string[] = ['title', 'authors', 'categories', 'description', 'coverURL', 'isbn', 'publicationDate', 'actions'];

	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				private httpClient: HttpClient,
				public snackBar: MatSnackBar,
				private cd: ChangeDetectorRef) {
		this.filteredAuthors = this.authorsFormControl.valueChanges.pipe(
			startWith(null),
			map((author: string | null) => author ? this.filterAth(author) : this.availableAuthors.slice()));
		this.filteredCategories = this.categoriesFormControl.valueChanges.pipe(
			startWith(null),
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
			publicationDate: ['', [Validators.required]],
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
			params.value.publicationDate,
			params.value.description,
			params.value.tableOfContents,
			params.value.coverPictureUrl
		);
		console.log(body);
		if (!this.toUpdate) {
			this.http.save('bookDetails', body).subscribe((response) => {
				if (response.success) {
					this.clearForm();
					this.getBookDetails();
					this.openSnackBar('Book details added successfully!', 'OK');
				}
			});
		} else {
			this.http.update('bookDetails', this.toUpdate.id, body).subscribe((response) => {
				if (response.success) {
					this.toUpdate = null;
					this.clearForm();
					this.getBookDetails();
					this.openSnackBar('Book details edited successfully!', 'OK');
				}

			});
		}
	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.allCategories = response.object;
		this.availableCategories = this.categoriesToString(this.allCategories).sort();
		this.bookDetailsParams.patchValue({ 'categories': '' });
	}

	async getAuthors() {
		const response: MessageInfo = await this.http.getAll('author/getAll');
		this.allAuthors = response.object;
		this.availableAuthors = this.authorsToString(this.allAuthors);
		this.bookDetailsParams.patchValue({ 'authors': '' });
	}

	async getBookDetails() {
		const response: MessageInfo = await this.http.getAll('bookDetails/getAll');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginator;

		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
	}

	uploadFile(event) {
		this.uploadingFile = true;
		this.fileToUpload = <File>event.target.files[0];
		const fd = new FormData();
		fd.append('picture', this.fileToUpload);
		this.httpClient.post(API_URL + '/api/v1/pictures/upload', fd).subscribe((response: MessageInfo) => {
			if (response.success) {
				this.bookDetailsParams
				.patchValue({ 'coverPictureUrl': response.object });
				this.uploadingFile = false;
			}
		})
	}

	getInfoFromAPI() {
		if (this.bookDetailsParams.get('isbn').value.length == 13) {
			this.httpClient.get<any>('https://api.itbook.store/1.0/books/' + this.bookDetailsParams.get('isbn').value)
			.subscribe(data => {
				if (data['title']) {
					this.bookDetailsParams.patchValue({
						'coverPictureUrl': data['image'],
						'description': data['desc'],
						'tableOfContents': 'string',
						'title': data['title'],
					});
					const authors = data['authors'].toString().trim().split(", ");
					authors.forEach(element => {
						const authorDTO = new AuthorDTO(element);
						const author = new Author(null, element);
						this.selectedAuthors.push(element);
						this.allAuthors.push(author);

					});
				} else
					this.httpClient.get<any>('https://www.googleapis.com/books/v1/volumes?q=' + this.bookDetailsParams.get('isbn').value)
					.subscribe(data => {
						this.bookDetailsParams.patchValue({
							'coverPictureUrl': data['items'][0].volumeInfo.imageLinks.thumbnail,
							'description': data['items'][0].volumeInfo.description,
							'tableOfContents': 'string',
							'title': data['items'][0].volumeInfo.title,
						});
						const authors = data['items'][0].volumeInfo.authors;
						authors.forEach(element => {
							const author = element.trim().toString().split(" ");
							if (this.selectedAuthors) {

								// this.selectedAuthors.push({
								// 	id: Math.random(),
								// 	authorFullName: author[0],
								// 	authorSurname: author[author.length - 1]
								// });
							}
						});
					});
			});
		}
	}

	editBookDetails(bookDetails: BookDetails) {
		this.bookDetailsParams.patchValue({
			'coverPictureUrl': bookDetails.coverPictureUrl,
			'publicationDate': new Date(bookDetails.publicationDate),
			'description': bookDetails.description,
			'isbn': bookDetails.isbn,
			'tableOfContents': bookDetails.tableOfContents,
			'title': bookDetails.title
		});
		this.toUpdate = bookDetails;
		this.selectedAuthors = this.authorsToString(bookDetails.authors);
		this.selectedCategories = this.categoriesToString(bookDetails.categories);
		this.availableCategories = this.categoriesToString(this.allCategories);
		this.availableAuthors = this.authorsToString(this.allAuthors);
		bookDetails.authors.forEach((ath) => {
			const index = this.availableAuthors.indexOf(ath.authorFullName);
			this.availableAuthors.splice(index, 1);
		});
		bookDetails.categories.forEach((cat) => {
			const index = this.availableCategories.indexOf(cat.bookCategoryName);
			this.availableCategories.splice(index, 1);
		});
		this.bookDetailsParams.patchValue({ 'categories': '' });
		this.bookDetailsParams.patchValue({ 'authors': '' });
	}

	async removeBookDetails(id: number) {
		await this.http.remove('bookDetails', id).subscribe((response) => {
			if (response.success) {
				this.openSnackBar('Book details removed successfully!', 'OK');
			}
			this.getBookDetails();
		});
	}

	clearForm() {
		this.bookDetailsParams.reset();
		this.bookDetailsParams.markAsPristine();
		this.bookDetailsParams.markAsUntouched();

		this.availableAuthors.push(...this.selectedAuthors);
		this.availableCategories.push(...this.selectedCategories);
		this.selectedCategories = [];
		this.selectedAuthors = [];
		this.cd.markForCheck();
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	// authors chips
	addAth(event: MatChipInputEvent): void {
		if (!this.autoAuthor.isOpen) {
			const input = event.input;
			const value = event.value;

			if ((value || '').trim()) {
				if (!this.selectedAuthors.includes(value)) {
					this.selectedAuthors.push(value.trim());
					this.allAuthors.push(new Author(null, value));
					this.availableAuthors.push(value);
				}
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
			if (!this.availableAuthors.includes(this.selectedAuthors[index])) {
				this.availableAuthors.push(this.selectedAuthors[index]);
			}
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
		return this.availableAuthors.filter(author => author.toLowerCase().indexOf(value.toLowerCase()) === 0).sort();
	}

	authorsToString(authors: Author[]): string[] {
		const arr = [];
		authors.forEach((val) => {
			arr.push(val.authorFullName);
		});
		return arr.sort();
	}

	authorsToAuthor(authors: string[]): Author[] {
		const arr: Author[] = [];
		authors.forEach((val) => {
			arr.push(this.allAuthors.filter(e => e.authorFullName === val)[0]);
		});
		return arr;
	}

	// categories chips
	addCat(event: MatChipInputEvent): void {
		if (!this.autoCategory.isOpen) {
			const input = event.input;
			const value = event.value;

			if ((value || '').trim()) {
				if (!this.selectedCategories.includes(value)) {
					this.selectedCategories.push(value.trim());
					this.allCategories.push(new BookCategory(null, value));
					this.availableCategories.push(value);
				}
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
			if (!this.availableCategories.includes(this.selectedCategories[index])) {
				this.availableCategories.push(this.selectedCategories[index]);
			}
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
		return this.availableCategories.filter(category => category.toLowerCase().indexOf(value.toLowerCase()) === 0).sort();
	}

	categoriesToString(categories: BookCategory[]): string[] {
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

	// snackbar
	openSnackBar(message: string, action: string) {
		this.snackBar.open(message, action);
	}
}
