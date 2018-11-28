import { Component, OnInit } from '@angular/core';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { BookCategory } from '../../../../models/database/entites/BookCategory';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookCategoryDTO } from '../../../../models/database/DTOs/BookCategoryDTO';
import { MatTableDataSource } from '@angular/material';

@Component({
	selector: 'app-manage-categories',
	templateUrl: './manage-categories.component.html',
	styleUrls: ['./manage-categories.component.css']
})
export class ManageCategoriesComponent implements OnInit {

	categoryParams: FormGroup;

	dataSource = new MatTableDataSource<BookCategory>();
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
			this.getCategories();
		});

		this.categorySubmitted = false;
	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.dataSource =  new MatTableDataSource( response.object);
	}

	editCategory(category: BookCategory) {
		this.categoryParams.patchValue({
			'categoryName': category.bookCategoryName
		});
	}

	async removeCategory(id: number) {
		await this.http.remove('bookCategory/remove/' + `${id}`).subscribe(() => {
			this.getCategories();
		});
	}
}
