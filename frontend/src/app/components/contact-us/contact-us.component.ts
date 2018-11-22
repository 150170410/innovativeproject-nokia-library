import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EmailMessage } from '../../models/EmailMessage';
import { MatDialogRef } from '@angular/material';

@Component({
	selector: 'app-contact-us',
	templateUrl: './contact-us.component.html',
	styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {

	contactParams: FormGroup;

	categories = ['Report a bug', 'Request new feature', 'Other'];
	emails = []; // TODO: to which emails these messages should be sent?

	constructor(private formBuilder: FormBuilder,
				public dialogRef: MatDialogRef<ContactUsComponent>) {
	}

	ngOnInit() {
		this.initForm();
	}

	initForm() {
		this.contactParams = this.formBuilder.group({
			category: '',
			title: '',
			message: ''
		});
	}

	sendEmail(contactParams: FormGroup) {
		const email = new EmailMessage(contactParams.value.category, contactParams.value.title, contactParams.value.message);
		console.log('sending email');
		// TODO: send emails

	}

}
