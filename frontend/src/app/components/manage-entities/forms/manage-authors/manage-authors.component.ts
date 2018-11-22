import { Component, OnInit } from '@angular/core';
import { Author } from '../../../../models/database/entites/Author';
import { RestService } from '../../../../services/rest/rest.service';
import { MessageInfo } from '../../../../models/MessageInfo';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthorDTO } from '../../../../models/database/DTOs/AuthorDTO';

@Component({
	selector: 'app-manage-authors',
	templateUrl: './manage-authors.component.html',
	styleUrls: ['./manage-authors.component.css']
})
export class ManageAuthorsComponent implements OnInit {
	
	authorParams: FormGroup;
	authors: Author[] = [];
	displayedAuthorsColumn: string[] = ['authorName', 'authorSurname'];

	// variables helpful for mistakes catching
	authorSubmitted = false;

	constructor(private formBuilder: FormBuilder, private http: RestService) {
	}

	ngOnInit() {
		this.initAuthorForm();
	}

	initAuthorForm() {
		this.authorParams = this.formBuilder.group({
			authorName: ['', [Validators.required, Validators.maxLength(300)]],
			authorSurname: ['', [Validators.required, Validators.maxLength(300)]],
			authorDescription: ['', Validators.maxLength(10000)]
		});
		this.getAuthors();
	}

	createAuthor(params: any) {

		this.authorSubmitted = true;

		if (this.authorParams.invalid) {
			console.log('mistakes in parameters');
			return;
		}

		const body = new AuthorDTO(params.value.authorName, params.value.authorSurname, params.value.authorDescription);
		console.log(body);
		this.http.save('author/create', body).subscribe(() => {
			console.log('author created');
		});

		// MUST BE DELETED AFTER BACKEND VALIDATION
		this.getAuthors();
		// MUST BE DELETED AFTER BACKEND VALIDATION

		this.authorSubmitted = false;
	}

	async getAuthors() {
		const response: MessageInfo = await this.http.getAll('author/getAll');
		this.authors = response.object;
	}

	autoFillAuthorForm() {
		this.authorParams.patchValue(new AuthorDTO('JRR', 'Tolkien', 'frodo and shit'));
	}


}
