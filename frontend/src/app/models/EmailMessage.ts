export class EmailMessage {
	category: string;
	title: string;
	message: string;

	constructor(category: string, title: string, message: string) {
		this.category = category;
		this.title = title;
		this.message = message;
	}
}
