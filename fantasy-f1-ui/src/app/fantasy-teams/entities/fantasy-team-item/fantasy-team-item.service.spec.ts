import { TestBed } from '@angular/core/testing';

import { FantasyTeamItemService } from './fantasy-team-item.service';

describe('FantasyTeamService', () => {
  let service: FantasyTeamItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FantasyTeamItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
