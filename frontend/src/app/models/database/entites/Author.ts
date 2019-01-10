export class Author {
	id: number;
	authorFullName: string;
	isRemovable: boolean;

	constructor(id: number, authorFullName: string) {
		this.id = id;
		this.authorFullName = authorFullName;
	}
}
