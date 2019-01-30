import { Component, OnInit, ViewChild } from '@angular/core';
import { Author } from '../../../../models/database/entites/Author';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthorDTO } from '../../../../models/database/DTOs/AuthorDTO';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { ConfirmationDialogService } from '../../../../services/confirmation-dialog/confirmation-dialog.service';

@Component({
	selector: 'app-manage-authors',
	templateUrl: './manage-authors.component.html',
	styleUrls: ['./manage-authors.component.css', '../../admin-panel.component.scss']
})
export class ManageAuthorsComponent implements OnInit {

	authorParams: FormGroup;
	formMode: string = 'Add';
	toUpdate: Author = null;

	//table
	@ViewChild(MatPaginator) paginator: MatPaginator;
	dataSource = new MatTableDataSource<Author>();
	displayedAuthorsColumn: string[] = ['authorFullName', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				public snackbar: SnackbarService,
				public confirmService: ConfirmationDialogService) {
	}

	ngOnInit() {
		this.initAuthorForm();
		this.getAuthors();
	}

	initAuthorForm() {
		this.authorParams = this.formBuilder.group({
			authorFullName: ['', [Validators.required, Validators.maxLength(300)]],
		});
	}

	createAuthor(params: any) {
		const body = new AuthorDTO(
			params.value.authorFullName);
		if (!this.toUpdate) {
			this.http.save('author/create', body).subscribe((response) => {
				if (response.success) {
					this.clearForm();
					this.getAuthors();
					this.snackbar.snackSuccess(response.message, 'OK');
				} else {
					this.snackbar.snackError('Error', 'OK');
				}
			}, (error) => {
				this.snackbar.snackError(error.error.message, 'OK');
			});
		} else {
			this.http.update('author', this.toUpdate.id, body).subscribe((response) => {
				if (response.success) {
					this.toUpdate = null;
					this.clearForm();
					this.getAuthors();
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

	async getAuthors() {
		const response: MessageInfo = await this.http.getAll('author/getAll');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.sort = this.sort;
	}

	editAuthor(author: Author) {
		this.authorParams.patchValue({
			'authorFullName': author.authorFullName,
		});
		this.formMode = 'Update';
		this.toUpdate = author;
		document.getElementById('admin-panel-tabs').scrollIntoView();
	}

	async removeAuthor(id: number) {
		await this.confirmService.openDialog().subscribe((result) => {
			if (result) {
				this.http.remove('author/remove/', id).subscribe((response) => {
					if (response.success) {
						this.snackbar.snackSuccess(response.message, 'OK');
					} else {
						this.snackbar.snackError('Error', 'OK');
					}
					this.getAuthors();
				}, (error) => {
					this.snackbar.snackError(error.error.message, 'OK');
				});
			}
		})
	}

	clearForm() {
		this.authorParams.reset();
		this.authorParams.markAsPristine();
		this.authorParams.markAsUntouched();
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	cancelUpdate() {
		this.toUpdate = null;
		this.clearForm();
	}
}
