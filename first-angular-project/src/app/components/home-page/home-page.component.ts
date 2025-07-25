import { CapitalizePipe } from '../../pipes/capitalize.pipe';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NavbarComponent } from '../navbar/navbar.component';
import { RouterOutlet } from '@angular/router';
import { Page, User } from '../../services/auth.types';
import { AuthOnlyDirective } from '../../directives/auth-only.directive';
import { AuthService } from '../../services/auth.service';

type DogImageApi = {
  message: string;
  status: string;
};

@Component({
  selector: 'app-home-page',
  imports: [NavbarComponent, RouterOutlet, CapitalizePipe, AuthOnlyDirective],
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomePageComponent {
  private readonly http = inject(HttpClient);
  authService = inject(AuthService);
  isLoggedIn = this.authService.isAuthenticatedSignal;

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

  public getUsers() {
    this.http
      .get<Page<User>>('http://localhost:8080/users?page=0&size=10')
      .subscribe((response) => {
        //TODO afiseaza users
      });
  }
}
