import { Component, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NavbarComponent } from './components/navbar/navbar.component';
import { NgIf } from '@angular/common';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NavbarComponent, RouterOutlet, NgIf],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  imageUrl = signal<string | null>(null);
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  constructor(private http: HttpClient) {}

  showDog() {
    this.loading.set(true);
    this.errorMessage.set(null);
    this.imageUrl.set(null);

    this.http.get<{ message: string }>('https://dog.ceo/api/breeds/image/random').subscribe({
      next: (data) => {
        this.imageUrl.set(data.message);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('Failed to load dog image');
        this.loading.set(false);
      },
    });
  }
}
