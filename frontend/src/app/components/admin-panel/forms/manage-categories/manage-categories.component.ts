import { Component, OnInit, ViewChild } from '@angular/core';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { BookCategory } from '../../../../models/database/entites/BookCategory';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookCategoryDTO } from '../../../../models/database/DTOs/BookCategoryDTO';
import { MatPaginator, MatTableDataSource } from '@angular/material';

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

	@ViewChild(MatPaginator) paginator: MatPaginator;

	constructor(private formBuilder: FormBuilder,
				private http: RestService) {
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
		const body = new BookCategoryDTO(params.value.categoryName);
		if (this.isUpdating == false) {
			this.http.save('bookCategory', body).subscribe(() => {
				this.getCategories();
			});
		} else {
			this.http.update('bookCategory', this.toUpdate.id, body).subscribe((respone) => {
				this.getCategories();
				this.isUpdating = false;
				this.clearForm();
			});
		}
	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginator;
	}

	editCategory(category: BookCategory) {
		this.categoryParams.patchValue({
			'categoryName': category.bookCategoryName
		});
		this.isUpdating = true;
		this.toUpdate = category;
	}

	async removeCategory(id: number) {
		await this.http.remove('bookCategory', id).subscribe(() => {
			this.getCategories();
		});
	}

	clearForm() {
		this.categoryParams.reset();
		this.categoryParams.markAsUntouched();
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	autoFillCategoryForm() {
		this.categoryParams.patchValue({ 'categoryName': 'cat' + Math.floor(Math.random() * 100) });
	}
}
