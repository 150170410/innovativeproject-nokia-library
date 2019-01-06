import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  error: boolean;

	constructor(private formBuilder: FormBuilder,
				private authService: AuthService) {
    this.error = (sessionStorage.getItem('isSignedCorrectly') === 'true') ||
            (sessionStorage.getItem('isSignedCorrectly') === null);

    console.log(sessionStorage.getItem('isSignedCorrectly'));
	}

	loginParams: FormGroup;

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
		sessionStorage.setItem('username', params.value.email);
		sessionStorage.setItem('password', params.value.password);
		this.loginParams.reset();
		this.authService.loginUser().then(
      () => this.error = (sessionStorage.getItem('isSignedCorrectly') === 'true')
    );
	}
}
