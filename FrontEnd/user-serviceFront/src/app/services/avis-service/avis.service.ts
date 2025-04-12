import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Avis } from 'src/app/models/Avis';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root'
})
export class AvisService {

  private apiUrl = 'http://localhost:8083/api/avis'; // adapte au port de ton microservice

  constructor(private http: HttpClient,private authService: AuthService ) {}

 
  createAvis(avis: Avis): Observable<Avis> {
    const userId = this.authService.getCurrentUserId(); // ou depuis le token/localStorage
    const headers = new HttpHeaders({
      'X-User-Id': userId.toString()
    });
    return this.http.post<Avis>(`${this.apiUrl}/addAvis`, avis, { headers });
  }
  

  getAllAvis(): Observable<Avis[]> {
    return this.http.get<Avis[]>(`${this.apiUrl}`);
  }
  
}
