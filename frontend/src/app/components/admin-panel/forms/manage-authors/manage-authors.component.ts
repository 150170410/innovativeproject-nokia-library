import { Component, OnInit, ViewChild } from '@angular/core';
import { Author } from '../../../../models/database/entites/Author';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthorDTO } from '../../../../models/database/DTOs/AuthorDTO';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { BookCategoryDTO } from '../../../../models/database/DTOs/BookCategoryDTO';

@Component({
	selector: 'app-manage-authors',
	templateUrl: './manage-authors.component.html',
	styleUrls: ['./manage-authors.component.css']
})
export class ManageAuthorsComponent implements OnInit {

	authorParams: FormGroup;

	dataSource = new MatTableDataSource<Author>();
	displayedAuthorsColumn: string[] = ['authorName', 'authorSurname', 'authorDescription', 'actions'];

	isUpdating = false;
	toUpdate: Author;

	@ViewChild(MatPaginator) paginator: MatPaginator;

	constructor(private formBuilder: FormBuilder,
				private http: RestService) {
	}

	ngOnInit() {
		this.initAuthorForm();
		this.getAuthors();
	}

	initAuthorForm() {
		this.authorParams = this.formBuilder.group({
			authorName: ['', [Validators.required, Validators.maxLength(300)]],
			authorSurname: ['', [Validators.required, Validators.maxLength(300)]],
			authorDescription: ['', Validators.maxLength(10000)]
		});
	}

	createAuthor(params: any) {

		if (this.isUpdating == false) {
			const body = new BookCategoryDTO(params.value.categoryName);
			this.http.save('author', body).subscribe(() => {
				this.getAuthors();
			});
		} else {
			const authorDTO = new AuthorDTO(params.value.authorName, params.value.authorSurname, params.value.authorDescription);
			this.http.update('author', this.toUpdate.id, authorDTO).subscribe((respone) => {
				this.getAuthors();
				this.isUpdating = false;
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
			'authorName': author.authorName,
			'authorSurname': author.authorSurname,
			'authorDescription': author.authorDescription
		});
		this.isUpdating = true;
		this.toUpdate = author;
	}

	async removeAuthor(id: number) {
		await this.http.remove('author', id).subscribe(() => {
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
			'authorName': 'J.R.R.',
			'authorSurname': 'Tolkien',
			'authorDescription': 'frodo ' + Math.floor(Math.random() * 100)
		});
	}
}
