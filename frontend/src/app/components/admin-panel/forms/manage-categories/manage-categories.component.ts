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

	isUpdating = false;
	toUpdate: BookCategory;

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
		if (this.isUpdating == false) {
			if (this.categoryParams.invalid) {
				console.log('mistakes in parameters');
				return;
			}

			const body = new BookCategoryDTO(params.value.categoryName);
			this.http.save('bookCategory', body).subscribe(() => {
				this.getCategories();
			});
		} else {
			console.log('update');
			this.http.update('bookCategory', this.toUpdate.id, new BookCategoryDTO(params.value.categoryName)).subscribe((respone) => {
				console.log('update worked');
				this.clearForm();
				this.isUpdating = false;
				this.getCategories();
				this.clearForm();
			})
		}
	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.dataSource = new MatTableDataSource(response.object);
	}

	clearForm() {
		this.categoryParams.reset();
	}

	editCategory(category: BookCategory) {
		this.categoryParams.patchValue({
			'categoryName': category.bookCategoryName
		});
		this.isUpdating = true;
		this.toUpdate = category;
	}

	async removeCategory(id: number) {
		await this.http.remove('bookCategory/remove/' + `${id}`).subscribe(() => {
			this.getCategories();
		});
	}
}
