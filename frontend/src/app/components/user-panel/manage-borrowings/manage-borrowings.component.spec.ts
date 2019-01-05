import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageBorrowingsComponent } from './manage-borrowings.component';

describe('ManageBorrowingsComponent', () => {
	let component: ManageBorrowingsComponent;
	let fixture: ComponentFixture<ManageBorrowingsComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ManageBorrowingsComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ManageBorrowingsComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
