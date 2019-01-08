export class Address {
  id: number;
  city: string;
  building: string;

  constructor(id: number, city: string, building: string) {
    this.id = id;
    this.city = city;
    this.building = building;
  }
}
