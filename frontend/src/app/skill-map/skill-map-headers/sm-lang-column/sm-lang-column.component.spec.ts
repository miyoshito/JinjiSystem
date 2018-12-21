import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SmLangColumnComponent } from './sm-lang-column.component';

describe('SmLangColumnComponent', () => {
  let component: SmLangColumnComponent;
  let fixture: ComponentFixture<SmLangColumnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SmLangColumnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SmLangColumnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
