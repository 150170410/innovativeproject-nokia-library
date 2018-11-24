import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListviewItemComponent } from './listview-item.component';

describe('ListviewItemComponent', () => {
  let component: ListviewItemComponent;
  let fixture: ComponentFixture<ListviewItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListviewItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListviewItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
