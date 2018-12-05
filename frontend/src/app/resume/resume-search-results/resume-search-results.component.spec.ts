import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResumeSearchResultsComponent } from './resume-search-results.component';

describe('ResumeSearchResultsComponent', () => {
  let component: ResumeSearchResultsComponent;
  let fixture: ComponentFixture<ResumeSearchResultsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResumeSearchResultsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResumeSearchResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
