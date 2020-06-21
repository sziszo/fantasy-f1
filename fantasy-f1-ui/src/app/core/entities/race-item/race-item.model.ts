export interface RaceItem {
  id: string;
  series: string;
  season: number;
  round: number;
  name: string;
  date: Date;
}

export interface FantasyRaceDefinition {
  series: string;
  season: number;
  fantasyRaces: RaceItem[];
  selectedFantasyRaceId: string;
}
