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
import { forEach } from '@angular/router/src/utils/collection';

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

	searchValue = '';
	expandedElement = null;

	// table
	@ViewChild(MatPaginator) paginator: MatPaginator;
	dataSource = new MatTableDataSource<BookDetails>();
	displayedBookDetailColumns: string[] = ['title', 'authors', 'categories', 'availableBooks'];
	@ViewChild(MatSort) sort: MatSort;

	isLoadingResults = true;

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

	searchBooks(val: string) {
		if (val === '') {
			this.dataSource.filter = '{';
		} else {
			this.dataSource.filter = val.trim().toLowerCase();
		}
	}

	filterByCategory(row): boolean {
		let contains = true;
		this.selectedCategories.forEach((category) => {
			if (!row.includes(category.toLowerCase())) {
				contains = false;
			}
		});
		return contains;
	}

	async getBooksDetails() {
		this.isLoadingResults = true;
		const response: MessageInfo = await this.http.getAll('bookDetails/getAll/available');
		this.dataSource = new MatTableDataSource(response.object.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return (data.title.toLowerCase().includes(filter.toLowerCase())
				|| JSON.stringify(data.authors).toLowerCase().includes(filter.toLowerCase()))
				&& this.filterByCategory(JSON.stringify(data.categories).toLowerCase())
				&& JSON.stringify(data).toLowerCase().includes(filter.toLowerCase())
		};
		this.dataSource.sort = this.sort;
		this.isLoadingResults = false;
	}

	async getCategories() {
		const response: MessageInfo = await this.http.getAll('bookCategory/getAll');
		this.availableCategories = this.categoriesToString(response.object).sort();
		this.categoriesFormControl.patchValue('');
	}

	removeCat(category: string): void {
		const index = this.selectedCategories.indexOf(category);
		if (index >= 0) {
			if (!this.availableCategories.includes(this.selectedCategories[index])) {
				this.availableCategories.push(this.selectedCategories[index]);
				this.availableCategories.sort();
			}
			this.selectedCategories.splice(index, 1).sort();
			if (this.searchValue === '' && this.selectedCategories.length === 0) {
				this.dataSource.filter = '{';
			}
		}
	}

	selectedCat(event: MatAutocompleteSelectedEvent): void {
		this.selectedCategories.push(event.option.viewValue);
		this.categoryInput.nativeElement.value = '';
		this.availableCategories = this.availableCategories.filter(e => e !== event.option.viewValue);
		this.categoriesFormControl.setValue(null);
		if (this.searchValue === '') {
			this.dataSource.filter = '{';
		}
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
