import { Component, inject } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { RouterOutlet } from '@angular/router';
import { MatInput } from '@angular/material/input';
import { CapitalizePipe } from '../../pipes/capitalize.pipe';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login-page',
  imports: [NavbarComponent, RouterOutlet, MatInput, CapitalizePipe],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss',
})
export class LoginPageComponent {
  authService = inject(AuthService);

  public login() {
    this.authService.login();
  }
}
