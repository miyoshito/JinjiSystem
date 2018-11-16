import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurriculumInsertComponent } from './curriculum-insert.component';

describe('CurriculumInsertComponent', () => {
  let component: CurriculumInsertComponent;
  let fixture: ComponentFixture<CurriculumInsertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurriculumInsertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurriculumInsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
