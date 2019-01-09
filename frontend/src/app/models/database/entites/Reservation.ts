import { User } from './User';
import { Book } from './Book';

export class Reservation {
	id: number;
	reservationDate: any;
	book: Book;
	user: User;
}