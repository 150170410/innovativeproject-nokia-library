import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmailDTO } from '../../models/EmailDTO';
import { MatDialogRef } from '@angular/material';
import { RestService } from '../../services/rest/rest.service';

@Component({
	selector: 'app-contact-us',
	templateUrl: './contact-us.component.html',
	styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {

	contactParams: FormGroup;

	categories = ['Report a bug', 'Request new feature', 'I don\'t like...', 'Other'];
	recipients = ['aabc0041@gmail.com']; // TODO: to which emails these messages should be sent?

	constructor(private formBuilder: FormBuilder,
				public dialogRef: MatDialogRef<ContactUsComponent>,
				private http: RestService) {
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
 		const email = new EmailDTO(category + ': ' + subject, context, this.recipients);
 		console.log(email);

		this.http.save('email/create', email).subscribe(() => {
			console.log('email sent');
			this.emailSent();
		});
	}

	emailSent(): void {
		this.dialogRef.close();
	}
}
