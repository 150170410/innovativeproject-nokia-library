import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookActionsComponent } from './book-actions.component';

describe('BookActionsComponent', () => {
	let component: BookActionsComponent;
	let fixture: ComponentFixture<BookActionsComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [BookActionsComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(BookActionsComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
