import { Routes } from '@angular/router';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { AboutPageComponent } from './components/about-page/about-page.component';
import { SettingsPageComponent } from './components/settings-page/settings-page.component';

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  {
    path: 'login',
    loadComponent: () =>
      import('./components/login-page/login-page.component').then((m) => m.LoginPageComponent),
  },
  { path: 'about', component: AboutPageComponent },
  { path: 'settings', component: SettingsPageComponent },
  { path: '**', component: NotFoundComponent },
];
