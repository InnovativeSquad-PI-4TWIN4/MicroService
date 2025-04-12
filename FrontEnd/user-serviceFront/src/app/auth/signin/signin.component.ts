import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/User';
import { Router } from '@angular/router'; // Ajouté pour rediriger l'utilisateur après la connexion

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html'
})
export class SigninComponent {
  user: User = { email: '', password: '' };
  errorMessage: string = ''; // Message d'erreur pour afficher si connexion échoue

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.authService.signIn(this.user).subscribe({
      next: res => {
        console.log('Signed in successfully!', res);
        localStorage.setItem('token', res.token); // Sauvegarde du token dans le localStorage
        
        // Optionnel : rediriger l'utilisateur après la connexion
        this.router.navigate(['/home']); // Remplace '/avis' par la route de ta page après connexion
      },
      error: err => {
        console.error('Error:', err);
        this.errorMessage = 'Invalid email or password. Please try again.'; // Message d'erreur
      }
    });
  }
}
