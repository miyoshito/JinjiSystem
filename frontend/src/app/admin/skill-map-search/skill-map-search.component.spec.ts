import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillMapSearchComponent } from './skill-map-search.component';

describe('SkillMapSearchComponent', () => {
  let component: SkillMapSearchComponent;
  let fixture: ComponentFixture<SkillMapSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillMapSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillMapSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
