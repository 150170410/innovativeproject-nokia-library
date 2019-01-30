import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleBookViewComponent } from './single-book-view';

describe('SingleBookViewComponent', () => {
	let component: SingleBookViewComponent;
	let fixture: ComponentFixture<SingleBookViewComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [SingleBookViewComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(SingleBookViewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
