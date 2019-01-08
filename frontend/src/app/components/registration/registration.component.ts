import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material";
import {UserDTO} from "../../models/database/DTOs/UserDTO";
import {RestService} from "../../services/rest/rest.service";
import {SnackbarService} from "../../services/snackbar/snackbar.service";
import {Router} from "@angular/router";

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

  constructor(private formBuilder: FormBuilder,
              private http: RestService,
              private snackbar: SnackbarService,
              private router: Router) { }

  ngOnInit() {
    this.initFormGroup();
  }

  initFormGroup() {
    this.registrationParams = this.formBuilder.group({
      name: ['', [Validators.required]],
      surname: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(7), Validators.maxLength(20)]],
      repeatedPassword: ['']
      },{
      validators: this.checkPasswords
    })
  }

  registrationButtonClick(params: any) {
    const body = new UserDTO(params.value.name, params.value.surname, params.value.password,
      params.value.email, null);

    this.http.save('user', body).subscribe((response) => {
      if (response.success) {
        this.router.navigateByUrl('login');
      } else {
        this.snackbar.snackError('Error', 'OK');
      }
    }, (error) => {
      this.snackbar.snackError(error.error.message, 'OK');
    });
  }

  checkPasswords(group: FormGroup) {
    let pass = group.controls.password.value;
    let confirmPass = group.controls.repeatedPassword.value;

    return pass === confirmPass ? null : { notSame: true }
  }
}
