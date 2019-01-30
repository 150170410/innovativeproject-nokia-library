import { User } from './User';
import { Book } from './Book';

export class Rental {
	id: number;
	rentalDate: Date;
	returnDate: Date;
	handOverDate: Date;
	user: User;
	book: Book;
	isCurrent: boolean;
}
