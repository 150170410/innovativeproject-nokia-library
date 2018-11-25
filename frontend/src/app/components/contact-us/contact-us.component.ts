import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmailDTO } from '../../models/EmailDTO';
import { MatDialogRef } from '@angular/material';

@Component({
	selector: 'app-contact-us',
	templateUrl: './contact-us.component.html',
	styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {

	contactParams: FormGroup;

	categories = ['Report a bug', 'Request new feature', 'Other'];
	recipients = ['nokia.library@gmail.com']; // TODO: to which emails these messages should be sent?

	constructor(private formBuilder: FormBuilder,
				public dialogRef: MatDialogRef<ContactUsComponent>) {
	}

	ngOnInit() {
		this.initForm();
	}

	initForm() {
		this.contactParams = this.formBuilder.group({
			category: ['', Validators.required],
			subject: ['', Validators.required],
			message: ['', Validators.required]
		});
	}

	sendEmail(contactParams: FormGroup) {
		const subject = contactParams.value.subject;
		const category = contactParams.value.category;
		const context = contactParams.value.message;
		console.log(contactParams);
 		const email = new EmailDTO(category + ' ' + subject, context, this.recipients);
 		console.log(email);

		this.emailSent();

		// TODO: send emails
	}

	emailSent(): void {
		this.dialogRef.close();
	}
}
