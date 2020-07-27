export interface DriverItem {
  id: string;

  series: string;
  season: number;
  driverId: string;

  name: string;
  permanentNumber: number;
  url: string;
  price: number;

  constructorId: string;
}

export interface DriverTableItem extends DriverItem {
  constructorName: string
  enabled: boolean
}
