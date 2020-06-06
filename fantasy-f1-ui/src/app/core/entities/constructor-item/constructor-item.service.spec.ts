import { TestBed } from '@angular/core/testing';

import { ConstructorItemService } from './constructor-item.service';

describe('ConstructorItemService', () => {
  let service: ConstructorItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConstructorItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
