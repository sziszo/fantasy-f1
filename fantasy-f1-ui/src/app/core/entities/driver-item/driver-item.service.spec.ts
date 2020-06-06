import { TestBed } from '@angular/core/testing';

import { DriverItemService } from './driver-item.service';

describe('DriverItemService', () => {
  let service: DriverItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DriverItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
