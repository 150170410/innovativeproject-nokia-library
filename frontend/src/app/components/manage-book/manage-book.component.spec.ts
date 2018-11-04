import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageBookComponent } from './manage-book.component';

describe('ManageBookComponent', () => {
	let component: ManageBookComponent;
	let fixture: ComponentFixture<ManageBookComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ManageBookComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ManageBookComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
