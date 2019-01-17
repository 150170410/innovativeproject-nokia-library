import { User } from './User';
import { Book } from './Book';

export class Rental {
	id: number;
	rentalDate: any;
	returnDate: any;
	handOverDate: any;
	user: User;
	book: Book;
	isCurrent: boolean;
	actualOwner: User;
}
