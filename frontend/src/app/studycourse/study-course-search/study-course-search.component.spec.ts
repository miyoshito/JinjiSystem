import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudyCourseSearchComponent } from './study-course-search.component';

describe('StudyCourseSearchComponent', () => {
  let component: StudyCourseSearchComponent;
  let fixture: ComponentFixture<StudyCourseSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudyCourseSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudyCourseSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
