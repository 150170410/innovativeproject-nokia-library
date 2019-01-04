import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BooksRequestedComponent } from './books-requested.component';

describe('BooksRequestedComponent', () => {
	let component: BooksRequestedComponent;
	let fixture: ComponentFixture<BooksRequestedComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [BooksRequestedComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(BooksRequestedComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
