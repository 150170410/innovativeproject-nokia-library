import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginParams: FormGroup;
  error = true;

	constructor(private formBuilder: FormBuilder,
				private authService: AuthService) {
	}

	ngOnInit() {
		this.initFormGroup();
	}

	initFormGroup() {
		this.loginParams = this.formBuilder.group({
			email: ['', [Validators.required, Validators.email]],
			password: ['', [Validators.required]]
		});
	}

	loginButtonClick(params: any) {
	  this.authService.loginUser(params.value.email, params.value.password).then( () => {
        this.error =  this.authService.isAuth;
    });
    this.loginParams.reset();
	}
}
