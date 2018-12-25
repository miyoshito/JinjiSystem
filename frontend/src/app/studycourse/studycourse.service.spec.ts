import { TestBed } from '@angular/core/testing';

import { StudycourseService } from './studycourse.service';

describe('StudycourseService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StudycourseService = TestBed.get(StudycourseService);
    expect(service).toBeTruthy();
  });
});
