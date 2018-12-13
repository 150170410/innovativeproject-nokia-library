import { Book } from './Book';

export class BookDetails {
	authors: [{
		authorFullName: string;
		id: number
	}];
	books: Book[]
	categories: [{
		bookCategoryName: string;
		id: number
	}];
	coverPictureUrl: string;
	publicationDate: Date;
	description: string;
	id: number;
	isbn: string;
	reviews: [{
		addDate: Date;
		comment: string;
		id: 0;
		user: {
			address: {
				build: string;
				city: string;
				id: number
			};
			email: string;
			firstName: string;
			id: number;
			lastName: string;
			reservations: [
				null
				];
			reviews: [
				null
				]
		}
	}];
	tableOfContents: string;
	title: string;
}
