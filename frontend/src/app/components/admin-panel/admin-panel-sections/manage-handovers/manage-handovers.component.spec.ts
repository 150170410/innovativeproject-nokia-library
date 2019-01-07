import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageHandoversComponent } from './manage-handovers.component';

describe('ManageHandoversComponent', () => {
	let component: ManageHandoversComponent;
	let fixture: ComponentFixture<ManageHandoversComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ManageHandoversComponent]
		})
		.compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(ManageHandoversComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
