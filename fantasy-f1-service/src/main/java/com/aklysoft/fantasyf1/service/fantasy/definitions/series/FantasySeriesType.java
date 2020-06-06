package com.aklysoft.fantasyf1.service.fantasy.definitions.series;

public enum FantasySeriesType {
  FORMULA_1("f1"), FORMULA_E("fe");

  public final String id;

  FantasySeriesType(String id) {
    this.id = id;
  }

  public static FantasySeriesType getFantasySeriesType(String id) {
    if (id != null) {
      for (FantasySeriesType fantasySeriesType : FantasySeriesType.values()) {
        if (fantasySeriesType.id.equals(id)) {
          return fantasySeriesType;
        }
      }
    }
    return null;
  }
}
