import { Component,OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {
  constructor(public authService: AuthService) { }

  ngOnInit(): void {}

  // Vérifier si l'utilisateur est connecté
  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  // Se déconnecter
  logout(): void {
    this.authService.logout();
    window.location.href='/signin';
  }
}
