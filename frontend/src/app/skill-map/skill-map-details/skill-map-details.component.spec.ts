import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillMapDetailsComponent } from './skill-map-details.component';

describe('SkillMapDetailsComponent', () => {
  let component: SkillMapDetailsComponent;
  let fixture: ComponentFixture<SkillMapDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillMapDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillMapDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
