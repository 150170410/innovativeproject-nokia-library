export class Author {
	id: number;
	authorName: string;
	authorSurname: string;
	authorDescription: string;

	constructor(id: number, authorName: string, authorSurname: string, authorDescription: string) {
		this.id = id;
		this.authorName = authorName;
		this.authorSurname = authorSurname;
		this.authorDescription = authorDescription;
	}

	toString(): string{
		return this.authorName + ' '+ this.authorSurname;
	}
}
