import { Book } from './Book';

export class User {
	id: number;
	email: string;
	firstName: string;
	lastName: string;
	address: any;
	roles: any;
	books: Book[];
}