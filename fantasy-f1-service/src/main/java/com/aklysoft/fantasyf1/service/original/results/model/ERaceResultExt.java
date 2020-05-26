package com.aklysoft.fantasyf1.service.original.results.model;

import com.aklysoft.fantasyf1.service.original.races.model.ERace;
import lombok.Builder;
import lombok.Data;

@Data
public class ERaceResultExt extends ERaceResult {

  private ERace race;

  @Builder
  public ERaceResultExt(ERace race, ERaceResult raceResult) {
    super(raceResult);
    this.race = race;
  }

  public int getRound() {
    return race.getRound();
  }

}
