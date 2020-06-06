export interface SeasonItem {
  id: string;
  season: number;
  series: string;
}

export interface FantasySeasonDefinitionItem {
  series: string;
  fantasySeasons: SeasonItem[];
  selectedFantasySeasonId: string;
}
