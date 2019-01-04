import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BooksHistoryComponent } from './books-history.component';

describe('BooksHistoryComponent', () => {
	let component: BooksHistoryComponent;
	let fixture: ComponentFixture<BooksHistoryComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [BooksHistoryComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(BooksHistoryComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
