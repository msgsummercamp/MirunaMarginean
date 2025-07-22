import { Component } from '@angular/core';
import { MatToolbar } from '@angular/material/toolbar';
import { MatAnchor, MatIconButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-navbar',
  imports: [MatToolbar, MatIcon, MatIconButton, MatAnchor, RouterLink, MatIcon, MatIcon],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {}
