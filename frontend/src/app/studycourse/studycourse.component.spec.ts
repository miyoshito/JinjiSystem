import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudycourseComponent } from './studycourse.component';

describe('StudycourseComponent', () => {
  let component: StudycourseComponent;
  let fixture: ComponentFixture<StudycourseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudycourseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudycourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
