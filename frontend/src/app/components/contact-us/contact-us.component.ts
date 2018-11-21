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
			category: '',
			title: '',
			message: ''
		});
	}

	sendEmail(contactParams: FormGroup) {
		const email = new EmailMessage(contactParams.value.category, contactParams.value.title, contactParams.value.message);
		// TODO: send emails

	}
}
