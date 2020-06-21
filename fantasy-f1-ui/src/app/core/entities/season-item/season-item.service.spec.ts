import { TestBed } from '@angular/core/testing';

import { SeasonItemService } from './season-item.service';

describe('SeasonItemService', () => {
  let service: SeasonItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SeasonItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
