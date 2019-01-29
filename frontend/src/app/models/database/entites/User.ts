import { Book } from './Book';
import {Role} from './Role';

export class User {
	id: number;
	email: string;
	firstName: string;
	lastName: string;
	address: any;
	roles: Role[];
	books: Book[];

	constructor(id: number, email: string, firstName: string, lastName: string, address: any, roles: Role[], books: Book[]) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.roles = roles;
		this.books = books;
	}
}
