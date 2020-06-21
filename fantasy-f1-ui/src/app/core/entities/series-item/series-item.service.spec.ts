import { TestBed } from '@angular/core/testing';

import { SeriesItemService } from './series-item.service';

describe('SeriesItemService', () => {
  let service: SeriesItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SeriesItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
