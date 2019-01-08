import {Address} from "../entites/Address";

export class UserDTO {
  firstName: string;
  lastName: string;
  password: string;
  email: string;
  address: Address;

  constructor(firstName: string, lastName: string, password: string, email: string, address: Address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.address = address;
  }
}
