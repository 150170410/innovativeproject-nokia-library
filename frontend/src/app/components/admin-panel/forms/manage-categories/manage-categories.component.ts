import { Component, OnInit } from '@angular/core';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { BookCategory } from '../../../../models/database/entites/BookCategory';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookCategoryDTO } from '../../../../models/database/DTOs/BookCategoryDTO';

@Component({
	selector: 'app-manage-categories',
	templateUrl: './manage-categories.component.html',
	styleUrls: ['./manage-categories.component.css']
})
export class ManageCategoriesComponent implements OnInit {

	categoryParams: FormGroup;

	bookCategories: BookCategory[] = [];
	displayedCategoryColumns: string[] = ['bookCategoryName', 'actions'];

	// variables helpful for mistakes catching
	categorySubmitted = false;

	constructor(private formBuilder: FormBuilder, private http: RestService) {
	}

	ngOnInit() {
		this.initCategoriesForm();
	}

	initCategoriesForm() {
		this.categoryParams = this.formBuilder.group({
			categoryName: ['', [Validators.required, Validators.maxLength(50)]]
		});
		this.getCategories();
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

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.bookCategories = response.object;
	}

	editCategory(category: BookCategory) {

	}

	removeCategory(id: number) {

	}
}
