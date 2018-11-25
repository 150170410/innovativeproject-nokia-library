export class EmailDTO{
	subject: string;
	messageContext: string;
	recipient: string[];

	constructor(subject: string, messageContext: string, recipient: string[]) {
		this.subject = subject;
		this.messageContext = messageContext;
		this.recipient = recipient;
	}
}