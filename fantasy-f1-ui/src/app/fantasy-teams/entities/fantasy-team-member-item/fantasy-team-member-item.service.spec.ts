import { TestBed } from '@angular/core/testing';

import { FantasyTeamMemberItemService } from './fantasy-team-member-item.service';

describe('FantasyTeamMemberItemService', () => {
  let service: FantasyTeamMemberItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FantasyTeamMemberItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
