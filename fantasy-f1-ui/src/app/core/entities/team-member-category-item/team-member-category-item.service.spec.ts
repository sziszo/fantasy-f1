import { TestBed } from '@angular/core/testing';

import { TeamMemberCategoryItemService } from './team-member-category-item.service';

describe('TeamMemberCategoryItemService', () => {
  let service: TeamMemberCategoryItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeamMemberCategoryItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
