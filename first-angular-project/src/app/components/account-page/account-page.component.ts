import { Component, inject, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { HttpClient } from '@angular/common/http';
import { jwtDecode, JwtPayload } from 'jwt-decode';
import { User } from '../../services/auth.types';

@Component({
  selector: 'app-account-page',
  imports: [NavbarComponent],
  templateUrl: './account-page.component.html',
  styleUrl: './account-page.component.scss',
})
export class AccountPageComponent implements OnInit {
  public email: string = '';
  public username: string = '';
  public firstname: string = '';
  public lastname: string = '';
  private http = inject(HttpClient);

  ngOnInit() {
    this.getUser();
  }

  public getUser() {
    const token = localStorage.getItem('token');
    let username;

    if (token) {
      const decoded = jwtDecode<JwtPayload>(token);
      username = decoded.sub;
    }

    this.http
      .get<User>('http://localhost:8080/users/username/' + username)
      .subscribe((response) => {
        this.email = response.email;
        this.username = response.username;
        this.firstname = response.firstname;
        this.lastname = response.lastname;
      });
  }
}
