import { Component, OnInit, ViewChild } from '@angular/core';
import { Author } from '../../../../models/database/entites/Author';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthorDTO } from '../../../../models/database/DTOs/AuthorDTO';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';

@Component({
	selector: 'app-manage-authors',
	templateUrl: './manage-authors.component.html',
	styleUrls: ['./manage-authors.component.css', '../../admin-panel.component.css']
})
export class ManageAuthorsComponent implements OnInit {

	authorParams: FormGroup;
	formMode: string = 'Add';
	toUpdate: Author = null;

	//table
	@ViewChild(MatPaginator) paginator: MatPaginator;
	dataSource = new MatTableDataSource<Author>();
	displayedAuthorsColumn: string[] = ['authorFullName', 'actions'];


	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				public snackbar: SnackbarService) {
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
			this.http.save('author', body).subscribe((response) => {
				if (response.success) {
					this.clearForm();
					this.getAuthors();
					this.snackbar.snackSuccess('Author added successfully!', 'OK');
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
					this.snackbar.snackSuccess('Author updated successfully!', 'OK');
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
	}

	editAuthor(author: Author) {
		this.authorParams.patchValue({
			'authorFullName': author.authorFullName,
		});
		this.formMode = 'Update';
		this.toUpdate = author;
	}

	async removeAuthor(id: number) {
		await this.http.remove('author', id).subscribe((response) => {
			if (response.success) {
				this.snackbar.snackSuccess('Author removed successfully!', 'OK');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
			this.getAuthors();
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	clearForm() {
		this.authorParams.reset();
		this.authorParams.markAsPristine();
		this.authorParams.markAsUntouched();
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
