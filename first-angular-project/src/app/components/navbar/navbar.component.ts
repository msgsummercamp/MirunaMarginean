import { Component, inject } from '@angular/core';
import { MatAnchor, MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { AuthOnlyDirective } from '../../directives/auth-only.directive';

@Component({
  selector: 'app-navbar',
  imports: [
    MatToolbar,
    MatIcon,
    MatIconButton,
    MatAnchor,
    RouterLink,
    RouterLinkActive,
    AuthOnlyDirective,
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {
  authService = inject(AuthService);

  isLoggedIn = this.authService.isAuthenticatedSignal;

  logout() {
    this.authService.logout();
  }
}
