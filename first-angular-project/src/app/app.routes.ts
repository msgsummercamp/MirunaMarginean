import { AboutPageComponent } from './components/about-page/about-page.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { Routes } from '@angular/router';
import { SettingsPageComponent } from './components/settings-page/settings-page.component';
import { authGuard } from './guards/auth.guard';
import { AccountPageComponent } from './components/account-page/account-page.component';

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  {
    path: 'login',
    loadComponent: () =>
      import('./components/login-page/login-page.component').then((m) => m.LoginPageComponent),
  },
  { path: 'about', component: AboutPageComponent },
  {
    path: 'settings',
    component: SettingsPageComponent,
    canActivate: [authGuard],
  },
  { path: 'account', component: AccountPageComponent },
  { path: '**', component: NotFoundComponent },
];
