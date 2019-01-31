import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MessageInfo } from '../../../models/MessageInfo';
import { RestService } from '../../../services/rest/rest.service';
import { BookDetailsService } from '../../../services/book-details/book-details.service';
import { BookDetails } from '../../../models/database/entites/BookDetails';
import { MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs/index';
import { map, startWith } from 'rxjs/operators';
import { BookCategory } from '../../../models/database/entites/BookCategory';

@Component({
	selector: 'app-table-view',
	templateUrl: './table-view.component.html',
	styleUrls: ['./table-view.component.scss'],
	animations: [
		trigger('detailExpand', [
			state('collapsed', style({ height: '0px', minHeight: '0', display: 'none' })),
			state('expanded', style({ height: '*' })),
			transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
		]),
	],
})
export class TableViewComponent implements OnInit {

	booksAll: BookDetails[] = [];
	books: BookDetails[] = [];
	listIsLoading = false;
	hideUnavailable = false;
	value = '';
	addresses = ['West Link', 'East Link'];
	expandedElement = null;

	// table
	@ViewChild(MatPaginator) paginator: MatPaginator;
	dataSource = new MatTableDataSource<BookDetails>();
	displayedBookDetailColumns: string[] = ['title', 'authors', 'categories', 'availableBooks'];
	@ViewChild(MatSort) sort: MatSort;

	// browser
	visible = true;
	selectable = true;
	removable = true;

	allCategories: BookCategory[] = [];
	availableCategories: string[] = [];
	selectedCategories: string[] = [];
	filteredCategories: Observable<string[]>;
	categoriesFormControl = new FormControl('');
	@ViewChild('categoryInput') categoryInput: ElementRef<HTMLInputElement>;
	@ViewChild('autoCat') autoCategory: MatAutocomplete;

	constructor(private bookDetailsService: BookDetailsService,
				private http: RestService) {
		this.filteredCategories = this.categoriesFormControl.valueChanges.pipe(
			startWith(null),
			map((category: string | null) => category ? this.filterCat(category) : this.availableCategories.slice()));
	}

	ngOnInit() {
		this.getBooksDetails();
		this.getCategories();
	}

	searchBooks(val) {
		// this.books = this.booksAll.filter((b) => {
		// 	return b.title.toLowerCase().includes(val.toLowerCase())
		// });
		this.dataSource.filter = val.trim().toLowerCase();
	}

	async getBooksDetails() {
		this.listIsLoading = true;
		const response: MessageInfo = await this.http.getAll('bookDetails/getAll/available');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return data.title.toLowerCase().includes(filter.toLowerCase())
				|| JSON.stringify(data.authors).toLowerCase().includes(filter.toLowerCase())
				|| JSON.stringify(data.categories).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
		this.listIsLoading = false;
	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.availableCategories = this.categoriesToString(response.object);
		console.log(this.availableCategories);
	}

	// categories chips
	addCat(event: MatChipInputEvent): void {
		if (!this.autoCategory.isOpen) {
			const input = event.input;
			const value = event.value;

			if ((value || '').trim()) {
				if (!this.selectedCategories.includes(value)) {
					this.selectedCategories.push(value.trim());
					this.allCategories.push(new BookCategory(null, value));
					this.availableCategories.push(value);
				}
			}

			if (input) {
				input.value = '';
			}

			this.categoriesFormControl.setValue(null);
		}
	}

	removeCat(category: string): void {
		const index = this.selectedCategories.indexOf(category);
		if (index >= 0) {
			if (!this.availableCategories.includes(this.selectedCategories[index])) {
				this.availableCategories.push(this.selectedCategories[index]);
			}
			this.selectedCategories.splice(index, 1);
		}
	}

	selectedCat(event: MatAutocompleteSelectedEvent): void {
		this.selectedCategories.push(event.option.viewValue);
		this.categoryInput.nativeElement.value = '';
		this.availableCategories = this.availableCategories.filter(e => e !== event.option.viewValue);
		this.categoriesFormControl.setValue(null);
	}

	categoriesToString(categories: BookCategory[]): string[] {
		const arr = [];
		categories.forEach((val) => {
			arr.push(val.bookCategoryName);
		});
		return arr;
	}

	private filterCat(value: string): string[] {
		return this.availableCategories.filter(category => category.toLowerCase().indexOf(value.toLowerCase()) === 0).sort();
	}
}
