import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmailMessage } from '../../models/EmailMessage';
import { MatDialogRef } from '@angular/material';
import { RestService } from '../../services/rest/rest.service';
import { SnackbarService } from '../../services/snackbar/snackbar.service';

@Component({
	selector: 'app-contact-us',
	templateUrl: './contact-us.component.html',
	styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {

	contactParams: FormGroup;
	categories = ['Report a bug', 'Request new feature', 'I don\'t like...',' Something is unclear', 'Other'];
	sendingFailed = false;
	errorMessage;

	constructor(private formBuilder: FormBuilder,
				public dialogRef: MatDialogRef<ContactUsComponent>,
				private http: RestService,
				public snackbar: SnackbarService) {
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
		const email = new EmailMessage(contactParams.value.category + ': ' + contactParams.value.subject, contactParams.value.message);
		this.dialogRef.close();
		this.http.save('email', email).subscribe((response) => {
			this.snackbar.snackSuccess('Message sent. Thank you!', 'OK');
		}, (error) => {
			this.snackbar.snackError('Something went wrong, but we still got the message.', 'OK');
		});

	}


}
