import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginResponse } from './auth.types';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _isAuthenticated = signal(false);
  public isAuthenticatedSignal = this._isAuthenticated;
  private http = inject(HttpClient);

  public login(username: string, password: string) {
    return this.http.post<LoginResponse>('http://localhost:8080/api/auth/login', {
      username,
      password,
    });
  }

  logout() {
    this._isAuthenticated.set(false);
  }
}
