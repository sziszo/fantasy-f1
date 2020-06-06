import { TestBed } from '@angular/core/testing';

import { RaceItemService } from './race-item.service';

describe('RaceItemService', () => {
  let service: RaceItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RaceItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
