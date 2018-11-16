import { TestBed } from '@angular/core/testing';

import { EmployeeMasterService } from './employee-master.service';

describe('EmployeeMasterService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EmployeeMasterService = TestBed.get(EmployeeMasterService);
    expect(service).toBeTruthy();
  });
});
