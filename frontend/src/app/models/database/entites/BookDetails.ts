export class BookDetails {
	authors: [{
		authorDescription: string;
		authorName: string;
		authorSurname: string;
		id: number
	}];
	books: []
	categories: [{
		bookCategoryName: string;
		id: number
	}];
	coverPictureUrl: string;
	dateOfPublication: Date;
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
