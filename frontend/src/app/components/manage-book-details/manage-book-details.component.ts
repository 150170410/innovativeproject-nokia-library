import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../../services/rest/rest.service';
import { BookCategoryDTO } from '../../models/DTOs/BookCategoryDTO';
import { AuthorDTO } from '../../models/DTOs/Author';
import { BookDetailsDTO } from '../../models/DTOs/BookDetailsDTO';
import { BookCategory } from '../../models/entites/BookCategory';
import { MessageInfo } from '../../models/entites/MessageInfo';


@Component({
	selector: 'app-manage-book-details',
	templateUrl: './manage-book-details.component.html',
	styleUrls: ['./manage-book-details.component.css']
})
export class ManageBookDetailsComponent implements OnInit {

	categoryParams: FormGroup;
	authorParams: FormGroup;
	bookDetailsParams: FormGroup;

	categories: BookCategory[] = [];
	displayedColumns: string[] = ['id', 'bookCategoryName'];

	constructor(private formBuilder: FormBuilder,
				private http: RestService) {
	}

	ngOnInit() {
		this.initCategoriesForm();
		this.initAuthorForm();
		this.initBookDetailsForm();
	}

	initCategoriesForm() {
		this.categoryParams = this.formBuilder.group({
			categoryName: ['', Validators.required]
		});
	}

	initAuthorForm() {
		this.authorParams = this.formBuilder.group({
			authorName: ['', Validators.required],
			authorSurname: ['', Validators.required],
			authorDescription: ['', Validators.required]
		});
	}

	initBookDetailsForm() {
		this.bookDetailsParams = this.formBuilder.group({
			coverPictureUrl: ['', Validators.required],
			dateOfPublication: ['', Validators.required],
			description: ['', Validators.required],
			isbn: ['', Validators.required],
			tableOfContents: ['', Validators.required],
			title: ['', Validators.required]
		});
	}

	createCategory(params: any) {
		const body = new BookCategoryDTO(params.value.categoryName);
		this.http.save('bookCategory/create', body).subscribe(() => {
			console.log('category created');
			this.getCategories();
		});

	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.categories = response.object;
	}

	createAuthor(params: any) {
		const body = new AuthorDTO(params.value.authorName, params.value.authorSurname, params.value.authorDescription);
		console.log(body);
		this.http.save('author/create', body).subscribe(() => {
			console.log('author created');
		});
	}

	createBookDetails(params: any) {

		const body = new BookDetailsDTO(params.value.coverPictureUrl, params.value.dateOfPublication,
			params.value.description, params.value.isbn,
			params.value.tableOfContents, params.value.title, [], [])
		this.http.save('bookDetails/create', body).subscribe(() => {
			console.log('book details created');
		});
	}
}
