import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmailMessage } from '../../models/EmailMessage';
import { MatDialogRef, MatSnackBar } from '@angular/material';
import { RestService } from '../../services/rest/rest.service';

@Component({
	selector: 'app-contact-us',
	templateUrl: './contact-us.component.html',
	styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {

	contactParams: FormGroup;
	categories = ['Report a bug', 'Request new feature', 'I don\'t like...', 'Other'];
	sendingFailed = false;
	errorMessage;

	constructor(private formBuilder: FormBuilder,
				public dialogRef: MatDialogRef<ContactUsComponent>,
				private http: RestService,
				public snackBar: MatSnackBar) {
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

		this.http.save('email', email).subscribe((response) => {
		});
		this.emailSent();
	}

	emailSent(): void {
		this.dialogRef.close();
		this.openSnackBar('Message sent!', 'OK');
	}

	openSnackBar(message: string, action: string) {
		this.snackBar.open(message, action, {
			duration: 3000,
		});
	}
}
