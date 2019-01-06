import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageRequestsComponent } from './manage-requests.component';

describe('ManageRequestsComponent', () => {
  let component: ManageRequestsComponent;
  let fixture: ComponentFixture<ManageRequestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageRequestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
