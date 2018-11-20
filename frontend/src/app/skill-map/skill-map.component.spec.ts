import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillMapComponent } from './skill-map.component';

describe('SkillMapComponent', () => {
  let component: SkillMapComponent;
  let fixture: ComponentFixture<SkillMapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillMapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
