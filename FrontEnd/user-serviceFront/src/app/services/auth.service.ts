import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8082/api/users'; // adapte l'URL à ton backend

  constructor(private http: HttpClient) {}

  signUp(user: User): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, user);
  }

  signIn(user: User): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.baseUrl}/signin`, user);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  // Sauvegarde du token après la connexion
  login(token: string): void {
    localStorage.setItem('token', token);
  }

  // Déconnexion de l'utilisateur
  logout(): void {
    localStorage.removeItem('token');
  }
  
  getCurrentUserId(): number {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user).id : 0;
  }

  sendResetPasswordEmail(email: string): Observable<any> {
    return this.http.post('/api/auth/forgot-password', { email });
  }
}
