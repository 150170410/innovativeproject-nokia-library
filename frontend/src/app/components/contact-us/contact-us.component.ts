import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmailMessage } from '../../models/EmailMessage';

@Component({
	selector: 'app-contact-us',
	templateUrl: './contact-us.component.html',
	styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {

	contactParams: FormGroup;

	categories = ['Report a bug', 'Request new feature', 'Other'];
	emails = []; // TODO: to which emails these messages should be sent?

	constructor(private formBuilder: FormBuilder) {
	}

	ngOnInit() {
		this.initForm();
	}

	initForm() {
		this.contactParams = this.formBuilder.group({
			category: ['', Validators.required],
			title: ['', Validators.required],
			message: ['', Validators.required]
		});
	}

	sendEmail(contactParams: FormGroup) {
		const email = new EmailMessage(contactParams.value.category, contactParams.value.title, contactParams.value.message);
		console.log(contactParams);
		console.log('sending email: ' + '' + email.category + ' ' + email.title + ' ' + email.message);
		// TODO: send emails
	}

}
