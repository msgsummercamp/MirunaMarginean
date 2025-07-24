import { CapitalizePipe } from '../../pipes/capitalize.pipe';
import { Component, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NavbarComponent } from '../navbar/navbar.component';
import { RouterOutlet } from '@angular/router';

type DogImageApi = {
  message: string;
  status: string;
};

@Component({
  selector: 'app-home-page',
  imports: [NavbarComponent, RouterOutlet, CapitalizePipe],
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent {
  private readonly http = inject(HttpClient);

  public readonly imageUrl = signal<string | null>(null);
  public readonly loading = signal(false);
  public readonly errorMessage = signal<string | null>(null);

  public showDog() {
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
