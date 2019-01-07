import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BooksReservedComponent } from './books-reserved.component';

describe('BooksReservedComponent', () => {
	let component: BooksReservedComponent;
	let fixture: ComponentFixture<BooksReservedComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [BooksReservedComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(BooksReservedComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
