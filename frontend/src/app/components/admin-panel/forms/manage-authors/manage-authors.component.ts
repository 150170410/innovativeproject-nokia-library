import { Component, OnInit, ViewChild } from '@angular/core';
import { Author } from '../../../../models/database/entites/Author';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthorDTO } from '../../../../models/database/DTOs/AuthorDTO';
import { MatPaginator, MatSnackBar, MatTableDataSource } from '@angular/material';

@Component({
	selector: 'app-manage-authors',
	templateUrl: './manage-authors.component.html',
	styleUrls: ['./manage-authors.component.css']
})
export class ManageAuthorsComponent implements OnInit {

	authorParams: FormGroup;

	toUpdate: Author = null;

	//table
	@ViewChild(MatPaginator) paginator: MatPaginator;
	dataSource = new MatTableDataSource<Author>();
	displayedAuthorsColumn: string[] = ['authorFullName', 'actions'];


	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				public snackBar: MatSnackBar) {
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
				if(response.success){
					this.openSnackBar('Author added successfully!', 'OK');
				}
				this.getAuthors();
			});
		} else {
			this.http.update('author', this.toUpdate.id, body).subscribe((response) => {
				if(response.success){
					this.openSnackBar('Author edited successfully!', 'OK');
				}
				this.getAuthors();
				this.toUpdate = null;
				this.clearForm();
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
		this.toUpdate = author;
	}

	async removeAuthor(id: number) {
		await this.http.remove('author', id).subscribe((response) => {
			if(response.success){
				this.openSnackBar('Author removed successfully!', 'OK');
			}
			this.getAuthors();
		});
	}

	clearForm() {
		this.authorParams.reset();
		this.authorParams.markAsUntouched();
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	autoFillAuthorForm() {
		this.authorParams.patchValue({
			'authorFullName': 'J.R.R. Tolkien'
		});
	}

	openSnackBar(message: string, action: string) {
		this.snackBar.open(message, action, {
			duration: 3000,
		});
	}
}
