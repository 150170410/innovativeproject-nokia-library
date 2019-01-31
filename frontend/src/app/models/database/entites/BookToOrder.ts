import {User} from './User';

export class BookToOrder {
	id: number;
	isbn: string;
	title: string;
	subscribed: boolean;
    removable: boolean;
	totalSubs: number;
}