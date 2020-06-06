export interface ConstructorItem {
  id: string;
  series: string;
  season: number;
  constructorId: string;
  name: string;
  nationality: string;
  url: string;
}

export interface ConstructorTableItem extends ConstructorItem {
  enabled: boolean;
}
