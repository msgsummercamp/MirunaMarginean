import { AuthService } from '../services/auth.service';
import { CanActivateFn, RedirectCommand, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticatedSignal()) {
    return true;
  }

  return new RedirectCommand(router.parseUrl('login'));
};
