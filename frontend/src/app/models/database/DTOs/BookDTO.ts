export class BookDTO {
	signature: string;
	bookDetailsId: number;
	comments: string;
	bookStatusId: number;
	owners: number[];

	constructor(signature: string, bookDetailsId: number, comments: string, bookStatusId: number, owners: number[]) {
		this.signature = signature;
		this.bookDetailsId = bookDetailsId;
		this.comments = comments;
		this.bookStatusId = bookStatusId;
		this.owners = owners;
	}
}