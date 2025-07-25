import { AuthService } from '../../services/auth.service';
import { CapitalizePipe } from '../../pipes/capitalize.pipe';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import {
  AbstractControl,
  FormControl,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';

type LoginForm = {
  email: FormControl<string>;
  username: FormControl<string>;
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
  private router = inject(Router);
  private readonly _authService = inject(AuthService);
  private readonly _formBuilder = inject(NonNullableFormBuilder);
  public loginError = signal<string | null>(null);

  public login() {
    this._authService.login(this.username.value, this.password.value).subscribe({
      next: (res) => {
        const token = res.token;
        localStorage.setItem('token', token);
        this.router.navigate(['']).then(() => this._authService.isAuthenticatedSignal.set(true));
      },
      error: (err) => {
        if (err.status === 401) {
          this.loginError.set('Invalid username or password.');
        } else {
          this.loginError.set('Something went wrong. Please try again later.');
        }
      },
    });
  }

  public readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', {
      validators: [Validators.required, Validators.email],
    }),
    username: this._formBuilder.control('', {
      validators: [Validators.required],
    }),
    password: this._formBuilder.control('', {
      validators: [Validators.required, Validators.minLength(8)],
    }),
  });

  public isFormControlInvalid(control: AbstractControl) {
    return control.invalid && control.touched;
  }

  public get email() {
    return this.loginFormGroup.controls.email;
  }

  public get username() {
    return this.loginFormGroup.controls.username;
  }

  public get password() {
    return this.loginFormGroup.controls.password;
  }

  public onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      this.login();
    }
  }
}
