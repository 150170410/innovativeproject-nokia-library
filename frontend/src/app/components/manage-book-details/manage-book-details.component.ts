import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../../services/rest/rest.service';
import { BookCategoryDTO } from '../../models/DTOs/BookCategoryDTO';

@Component({
	selector: 'app-manage-book-details',
	templateUrl: './manage-book-details.component.html',
	styleUrls: ['./manage-book-details.component.css']
})
export class ManageBookDetailsComponent implements OnInit {

	categoryParams: FormGroup;

	constructor(private formBuilder: FormBuilder,
				private http: RestService) {
	}

	ngOnInit() {
		this.initCategoriesForm();
	}

	initCategoriesForm() {
		this.categoryParams = this.formBuilder.group({
			categoryName: ['', Validators.required]
		});
	}

	createCategory(params: any) {
		const body = new BookCategoryDTO(params.value.categoryName)
		this.http.save('bookCategory/create', body).subscribe(() => {
			console.log('category created');
		});
	}


}
