import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudyCourseEditComponent } from './study-course-edit.component';

describe('StudyCourseEditComponent', () => {
  let component: StudyCourseEditComponent;
  let fixture: ComponentFixture<StudyCourseEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudyCourseEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudyCourseEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
