import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudyCourseResultsComponent } from './study-course-results.component';

describe('StudyCourseResultsComponent', () => {
  let component: StudyCourseResultsComponent;
  let fixture: ComponentFixture<StudyCourseResultsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudyCourseResultsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudyCourseResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
