import { Component, OnInit, ViewChild } from '@angular/core';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { BookCategory } from '../../../../models/database/entites/BookCategory';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookCategoryDTO } from '../../../../models/database/DTOs/BookCategoryDTO';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';

@Component({
	selector: 'app-manage-categories',
	templateUrl: './manage-categories.component.html',
	styleUrls: ['./manage-categories.component.css', '../../admin-panel.component.scss']
})
export class ManageCategoriesComponent implements OnInit {

	categoryParams: FormGroup;
	formMode: string = 'Add';
	toUpdate: BookCategory = null;

	//table
	@ViewChild(MatPaginator) paginator: MatPaginator;
	dataSource = new MatTableDataSource<BookCategory>();
	displayedCategoryColumns: string[] = ['bookCategoryName', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				public snackbar: SnackbarService,
				public confirmService: ConfirmationDialogService) {
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
			this.http.save('bookCategory/create', body).subscribe((response) => {
				if (response.success) {
					this.clearForm();
					this.getCategories();
					this.snackbar.snackSuccess(response.message, 'OK');
				} else {
					this.snackbar.snackError('Error', 'OK');
				}
			}, (error) => {
				this.snackbar.snackError(error.error.message, 'OK');
			});
		} else {
			this.http.update('bookCategory', this.toUpdate.id, body).subscribe((response) => {
				if (response.success) {
					this.toUpdate = null;
					this.clearForm();
					this.getCategories();
					this.formMode = 'Add';
					this.snackbar.snackSuccess(response.message, 'OK');
				} else {
					this.snackbar.snackError('Error', 'OK');
				}
			}, (error) => {
				this.snackbar.snackError(error.error.message, 'OK');
			});
		}
	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.sort = this.sort;
	}

	editCategory(category: BookCategory) {
		this.categoryParams.patchValue({
			'categoryName': category.bookCategoryName
		});
		this.formMode = 'Update';
		this.toUpdate = category;
		document.getElementById('admin-panel-tabs').scrollIntoView();
	}

	async removeCategory(id: number) {
		await this.confirmService.openDialog().subscribe((result) => {
			if (result) {
				this.http.remove('bookCategory/remove/', id).subscribe((response) => {
					if (response.success) {
						this.snackbar.snackSuccess(response.message, 'OK');
						this.getCategories();
					} else {
						this.snackbar.snackError('Error', 'OK');
					}
				}, (error) => {
					this.snackbar.snackError(error.error.message, 'OK');
				});
			}
		})
	}

	clearForm() {
		this.categoryParams.reset();
		this.categoryParams.markAsPristine();
		this.categoryParams.markAsUntouched();
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	cancelUpdate() {
		this.toUpdate = null;
		this.clearForm();
	}
}
