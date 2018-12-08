import { Component, OnInit, ViewChild } from '@angular/core';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { BookCategory } from '../../../../models/database/entites/BookCategory';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookCategoryDTO } from '../../../../models/database/DTOs/BookCategoryDTO';
import { MatPaginator, MatSnackBar, MatTableDataSource } from '@angular/material';

@Component({
	selector: 'app-manage-categories',
	templateUrl: './manage-categories.component.html',
	styleUrls: ['./manage-categories.component.css']
})
export class ManageCategoriesComponent implements OnInit {

	categoryParams: FormGroup;

	toUpdate: BookCategory = null;

	//table
	@ViewChild(MatPaginator) paginator: MatPaginator;
	dataSource = new MatTableDataSource<BookCategory>();
	displayedCategoryColumns: string[] = ['bookCategoryName', 'actions'];

	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				public snackBar: MatSnackBar) {
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
		if (!this.toUpdate) {
			this.http.save('bookCategory', body).subscribe((response) => {
				this.getCategories();
				if(response.success){
					this.openSnackBar('Category added successfully!', 'OK');
				}
			});
		} else {
			this.http.update('bookCategory', this.toUpdate.id, body).subscribe((response) => {
				if(response.success){
					this.openSnackBar('Category edited successfully!', 'OK');
				}
				this.getCategories();
				this.toUpdate = null;
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

		this.toUpdate = category;
	}

	async removeCategory(id: number) {
		await this.http.remove('bookCategory', id).subscribe((response) => {
			if(response.success){
				this.openSnackBar('Category removed successfully!', 'OK');
			}
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

	openSnackBar(message: string, action: string) {
		this.snackBar.open(message, action, {
			duration: 3000,
		});
	}
}
