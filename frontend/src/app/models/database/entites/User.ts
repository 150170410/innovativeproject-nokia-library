import { Book } from './Book';

export class User {
	id: number;
	email: string;
	firstName: string;
	lastName: string;
	address: any;
	roles: any;
	books: Book[];

	constructor(id: number, email: string, firstName: string, lastName: string, address: any, roles: any, books: Book[]) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.roles = roles;
		this.books = books;
	}

	 static all(){
		return [
			new User(1, 'janusz.pawlacz@nokia.com', 'Janusz', 'Pawlacz', 'west link', 'user', []),
			new User(2, 'jacek.zalewski@email.com', 'Jacek', 'Zalewski', 'nowhere', 'admin', [])
		];
	}

}
