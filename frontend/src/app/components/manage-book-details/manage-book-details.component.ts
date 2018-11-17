import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { RestService } from '../../services/rest/rest.service';
import { BookCategoryDTO } from '../../models/DTOs/BookCategoryDTO';
import { AuthorDTO } from '../../models/DTOs/Author';
import { BookDetailsDTO } from '../../models/DTOs/BookDetailsDTO';
import { BookCategory } from '../../models/entites/BookCategory';
import { MessageInfo } from '../../models/entites/MessageInfo';
import {Author} from '../../models/entites/Author';
import {BookDetails} from '../../models/entites/BookDetails';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {Observable} from 'rxjs';
import {MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent} from '@angular/material';
import {map, startWith} from 'rxjs/operators';
import {logger} from 'codelyzer/util/logger';

@Component({
	selector: 'app-manage-book-details',
	templateUrl: './manage-book-details.component.html',
	styleUrls: ['./manage-book-details.component.css']
})
export class ManageBookDetailsComponent implements OnInit {

	categoryParams: FormGroup;
	authorParams: FormGroup;
	bookDetailsParams: FormGroup;

	bookCategories: BookCategory[] = [];
	displayedCategoryColumns: string[] = ['id', 'bookCategoryName'];
	authors: Author[] = [];
  displayedAuthorsColumn: string[] = ['id', 'authorName', 'authorSurname'];
	booksDetails: BookDetails[] = [];
  displayedBookDetailColumns: string[] = ['id', 'title', 'isbn'];

  myCategories: BookCategory[] = [];

  /*
  Chip list for authors.
  */
  selectable = true;
  removable = true;
  addOnBlur = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  authorCtrl = new FormControl();
  filteredAuthors: Observable<string[]>;
  myAuthors: any[] = [];
  @ViewChild('authorInput') authorInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;
  /*
  Chip list for authors.
  */


	constructor(private formBuilder: FormBuilder,
				private http: RestService) {

	  this.filteredAuthors = this.authorCtrl.valueChanges.pipe(
      startWith(null),
      map((author: string | null) => author ? this._filter(author) : this.authors.slice()));
	}

	/*
	Chip list for authors
  */
  add(event: MatChipInputEvent): void {
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;

      if ((value || '').trim()) {
        this.myAuthors.push( {
          id: Math.random(),
          authorName: value.trim()
        });
      }

      if (input) {
        input.value = '';
      }

      this.authorCtrl.setValue(null);
    }
  }
  remove(author, index): void {
    if (index >= 0) {
      this.myAuthors.splice(index, 1);
    }
  }
  selected(event: MatAutocompleteSelectedEvent): void {
    this.myAuthors.push(event.option.value);
    this.authorInput.nativeElement.value = '';
    this.authorCtrl.setValue(null);
  }
  private _filter(value: any): any[] {
    return this.authors.filter(author => author.authorName.toLowerCase().includes(value.toLowerCase()));
  }
  /*
	Chip list for authors
	 */

  compareWithFn(item1, item2) {
    return item1 && item2 ? item1.id === item2.id : item1 === item2;
  }


	ngOnInit() {
		this.initCategoriesForm();
		this.initAuthorForm();
		this.initBookDetailsForm();
	}

	initCategoriesForm() {
		this.categoryParams = this.formBuilder.group({
			categoryName: ['', Validators.required]
		});
    this.getCategories();
	}

	initAuthorForm() {
		this.authorParams = this.formBuilder.group({
			authorName: ['', Validators.required],
			authorSurname: ['', Validators.required],
			authorDescription: ['', Validators.required]
		});
    this.getAuthors();
	}

	initBookDetailsForm() {
		this.bookDetailsParams = this.formBuilder.group({
			coverPictureUrl: ['', Validators.required],
			dateOfPublication: ['', Validators.required],
			description: ['', Validators.required],
			isbn: ['', Validators.required],
			tableOfContents: ['', Validators.required],
			title: ['', Validators.required]
		});
    this.getBookDetails();
	}

	createCategory(params: any) {
		const body = new BookCategoryDTO(params.value.categoryName);
		this.http.save('bookCategory/create', body).subscribe(() => {
			console.log('category created');
		});
	}

	createAuthor(params: any) {
		const body = new AuthorDTO(params.value.authorName, params.value.authorSurname, params.value.authorDescription);
		console.log(body);
		this.http.save('author/create', body).subscribe(() => {
			console.log('author created');
		});
	}

	createBookDetails(params: any) {
		const body = new BookDetailsDTO(params.value.coverPictureUrl, params.value.dateOfPublication,
			params.value.description, params.value.isbn,
			params.value.tableOfContents, params.value.title, this.myAuthors, this.myCategories)
		this.http.save('bookDetails/create', body).subscribe(() => {
			console.log('book details created');
			console.log(this.myCategories);
		});
	}

  async getCategories() {
    const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
    this.bookCategories = response.object;
  }

  async getAuthors() {
	  const response: MessageInfo = await this.http.getAll('author/getAll');
	  this.authors = response.object;
  }

  async getBookDetails() {
	  const response: MessageInfo = await this.http.getAll('bookDetails/getAll');
	  this.booksDetails = response.object;
  }
}
