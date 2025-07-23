import { Component, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RouterOutlet } from '@angular/router';

type DogImageApi = {
  message: string;
  status: string;
};

@Component({
  selector: 'app-root',
  imports: [NavbarComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  private http = inject(HttpClient);

  imageUrl = signal<string | null>(null);
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  showDog() {
    this.loading.set(true);
    this.errorMessage.set(null);
    this.imageUrl.set(null);

    this.http.get<DogImageApi>('https://dog.ceo/api/breeds/image/random').subscribe({
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
