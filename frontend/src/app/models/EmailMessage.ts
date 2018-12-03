export class EmailMessage{
	subject: string;
	messageContext: string;

	constructor(subject: string, messageContext: string) {
		this.subject = subject;
		this.messageContext = messageContext;
	}
}