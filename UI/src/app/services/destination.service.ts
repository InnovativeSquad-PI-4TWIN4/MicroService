import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Destination } from '../models/destination.model';

@Injectable({
  providedIn: 'root'
})
export class DestinationService {
  private apiUrl = 'http://localhost:8080/api/destinations'; // Adjust port if needed

  constructor(private http: HttpClient) { }

  getAllDestinations(): Observable<Destination[]> {
    return this.http.get<Destination[]>(this.apiUrl);
  }

  getDestinationById(id: number): Observable<Destination> {
    return this.http.get<Destination>(`${this.apiUrl}/${id}`);
  }

  createDestination(destination: Destination): Observable<Destination> {
    return this.http.post<Destination>(this.apiUrl, destination);
  }

  updateDestination(id: number, destination: Destination): Observable<Destination> {
    return this.http.put<Destination>(`${this.apiUrl}/${id}`, destination);
  }

  deleteDestination(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getDestinationsByCountry(country: string): Observable<Destination[]> {
    return this.http.get<Destination[]>(`${this.apiUrl}/country/${country}`);
  }

  searchDestinationsByName(name: string): Observable<Destination[]> {
    return this.http.get<Destination[]>(`${this.apiUrl}/search?name=${name}`);
  }
}
