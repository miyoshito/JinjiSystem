import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurriculumSearchComponent } from './curriculum-search.component';

describe('CurriculumSearchComponent', () => {
  let component: CurriculumSearchComponent;
  let fixture: ComponentFixture<CurriculumSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurriculumSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurriculumSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
