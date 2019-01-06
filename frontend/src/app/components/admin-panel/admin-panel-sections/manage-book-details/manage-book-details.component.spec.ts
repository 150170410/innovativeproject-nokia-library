import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageBookDetailsComponent } from './manage-book-details.component';

describe('ManageBookDetailsComponent', () => {
  let component: ManageBookDetailsComponent;
  let fixture: ComponentFixture<ManageBookDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageBookDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageBookDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
