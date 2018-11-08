export class BookDetails {
	authors: [{
		authorDescription: string;
		authorName: string;
		authorSurname: string;
		id: 0
	}];
	categories: [{
		bookCategoryName: string;
		id: 0
	}];
	coverPictureUrl: string;
	dateOfPublication: Date;
	description: string;
	id: 0;
	isbn: string;
	reviews: [{
		addDate: Date;
		comment: string;
		id: 0;
		user: {
			address: {
				build: string;
				city: string;
				id: 0
			};
			email: string;
			firstName: string;
			id: 0;
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
