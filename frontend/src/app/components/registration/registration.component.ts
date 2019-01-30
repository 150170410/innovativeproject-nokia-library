import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validator, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material';
import { UserDTO } from '../../models/database/DTOs/UserDTO';
import { RestService } from '../../services/rest/rest.service';
import { SnackbarService } from '../../services/snackbar/snackbar.service';
import { Router } from '@angular/router';
import {MessageInfo} from '../../models/MessageInfo';
import {Address} from '../../models/database/entites/Address';

export class MyErrorStateMatcher implements ErrorStateMatcher {
	isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
		const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
		const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);

		return (invalidCtrl || invalidParent);
	}
}

@Component({
	selector: 'app-registration',
	templateUrl: './registration.component.html',
	styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
	matcher = new MyErrorStateMatcher();
	registrationParams: FormGroup;

  addresses: Address[] = [];

	constructor(private formBuilder: FormBuilder,
				private http: RestService,
				private snackbar: SnackbarService,
				private router: Router) {
	}

	ngOnInit() {
		this.initFormGroup();
		this.initAddresses();
	}

  async initAddresses() {
    const response: MessageInfo = await this.http.getAll('address/getAll');
    this.addresses = response.object;
  }

	initFormGroup() {
		this.registrationParams = this.formBuilder.group({
      name: ['', [Validators.required]],
			surname: ['', [Validators.required]],
      address: ['', [Validators.required]],
			email: ['', [Validators.required, Validators.email]],
			password: ['', [Validators.required, Validators.minLength(7), Validators.maxLength(20)]],
			repeatedPassword: ['']
		}, {
			validators: this.checkPasswords
		});
	}

  getAddress(param: any) {
	  if (param) {
	    const address: Address = this.addresses.filter( (add) => add.id === param)[0];
	    return address;
    }
	  return null;
  }
	registrationButtonClick(params: any) {
		const body = new UserDTO(params.value.name, params.value.surname, params.value.password,
			params.value.email, this.getAddress(params.value.address));

		this.http.save('user/create', body).subscribe((response) => {
			if (response.success) {
				this.snackbar.snackSuccess(response.message, 'OK');
				this.router.navigateByUrl('login');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	checkPasswords(group: FormGroup) {
		const pass = group.controls.password.value;
		const confirmPass = group.controls.repeatedPassword.value;
   if (pass !== confirmPass) {
      return {notSame: true};
    }
   return null;
	}
}
