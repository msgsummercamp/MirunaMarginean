import { AuthService } from '../../services/auth.service';
import { CapitalizePipe } from '../../pipes/capitalize.pipe';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import {
  AbstractControl,
  FormControl,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { RouterOutlet } from '@angular/router';

type LoginForm = {
  email: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-login-page',
  imports: [NavbarComponent, RouterOutlet, CapitalizePipe, ReactiveFormsModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginPageComponent {
  private readonly _authService = inject(AuthService);
  private readonly _formBuilder = inject(NonNullableFormBuilder);

  public login() {
    this._authService.login();
  }

  public readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', {
      validators: [Validators.required, Validators.email],
    }),
    password: this._formBuilder.control('', {
      validators: [Validators.required, Validators.minLength(8)],
    }),
  });

  isFormControlInvalid(control: AbstractControl) {
    return control.invalid && control.touched;
  }

  public get email() {
    return this.loginFormGroup.controls.email;
  }

  public get password() {
    return this.loginFormGroup.controls.password;
  }

  public onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      console.log('getRawValue():', this.loginFormGroup.getRawValue());
      console.log('value:', this.loginFormGroup.value);
    }
  }
}
