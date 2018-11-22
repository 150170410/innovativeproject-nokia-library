export class AuthorDTO {
	authorName: string;
	authorSurname: string;
	authorDescription: string;

	constructor(authorName: string, authorSurname: string, authorDescription: string) {
		this.authorName = authorName;
		this.authorSurname = authorSurname;
		this.authorDescription = authorDescription;
	}
}
