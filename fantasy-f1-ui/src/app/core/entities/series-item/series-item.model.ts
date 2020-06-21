export interface SeriesItem {
  id: string;
  name: string;
}

export interface FantasySeriesDefinitionItem {
  fantasySeriesList: SeriesItem[];
  selectedFantasySeriesId: string;
}
