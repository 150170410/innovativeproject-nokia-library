import { Component, OnInit } from '@angular/core';
import { Author } from '../../../../models/database/entites/Author';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthorDTO } from '../../../../models/database/DTOs/AuthorDTO';
import { MatTableDataSource } from '@angular/material';

@Component({
	selector: 'app-manage-authors',
	templateUrl: './manage-authors.component.html',
	styleUrls: ['./manage-authors.component.css']
})
export class ManageAuthorsComponent implements OnInit {

	authorParams: FormGroup;
	dataSource = new MatTableDataSource<Author>();
	displayedAuthorsColumn: string[] = ['authorName', 'authorSurname', 'authorDescription', 'actions'];

	// variables helpful for mistakes catching
	authorSubmitted = false;

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

		this.authorSubmitted = true;

		if (this.authorParams.invalid) {
			console.log('mistakes in parameters');
			return;
		}

		const body = new AuthorDTO(params.value.authorName, params.value.authorSurname, params.value.authorDescription);
		this.http.save('author/create', body).subscribe(() => {
			this.getAuthors();
		});
		this.authorSubmitted = false;
	}

	async getAuthors() {
		const response: MessageInfo = await this.http.getAll('author/getAll');
		this.dataSource = new MatTableDataSource(response.object);
	}

	autoFillAuthorForm() {
		this.authorParams.patchValue(new AuthorDTO('JRR', 'Tolkien', 'frodo and shit'));
	}


	editAuthor(author: Author) {
		this.authorParams.patchValue({
			'authorName': author.authorName,
			'authorSurname': author.authorSurname,
			'authorDescription': author.authorDescription
		});
	}

	async removeAuthor(id: number) {
		await this.http.remove('author/remove/' + `${id}`).subscribe(() => {
			this.getAuthors();
		});
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
