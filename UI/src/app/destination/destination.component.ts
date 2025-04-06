import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DestinationService } from '../services/destination.service';
import { Destination } from '../models/destination.model';

@Component({
  selector: 'app-destination',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
    // Remove HttpClientModule from here - it should be provided at application level
  ],
  templateUrl: './destination.component.html',
  styleUrls: ['./destination.component.css']
})
export class DestinationComponent implements OnInit {
  destinations: Destination[] = [];
  newDestination: Destination = {
    name: '',
    country: '',
    averagePrice: 0
  };
  selectedDestination?: Destination;
  searchTerm: string = '';
  selectedCountry: string = '';

  constructor(private destinationService: DestinationService) { }

  ngOnInit(): void {
    this.loadDestinations();
  }

  loadDestinations(): void {
    this.destinationService.getAllDestinations().subscribe({
      next: (data) => this.destinations = data,
      error: (err) => console.error('Error fetching destinations', err)
    });
  }

  createDestination(): void {
    this.destinationService.createDestination(this.newDestination).subscribe({
      next: () => {
        this.loadDestinations();
        this.newDestination = { name: '', country: '', averagePrice: 0 };
      },
      error: (err) => console.error('Error creating destination', err)
    });
  }

  selectDestination(destination: Destination): void {
    this.selectedDestination = { ...destination };
  }

  updateDestination(): void {
    if (this.selectedDestination && this.selectedDestination.id) {
      this.destinationService.updateDestination(
        this.selectedDestination.id,
        this.selectedDestination
      ).subscribe(
        () => {
          this.loadDestinations();
          this.selectedDestination = undefined;
        },
        error => console.error('Error updating destination', error)
      );
    }
  }

  deleteDestination(id: number): void {
    if (confirm('Are you sure you want to delete this destination?')) {
      this.destinationService.deleteDestination(id).subscribe(
        () => this.loadDestinations(),
        error => console.error('Error deleting destination', error)
      );
    }
  }

  searchDestinations(): void {
    if (this.searchTerm) {
      this.destinationService.searchDestinationsByName(this.searchTerm).subscribe(
        data => this.destinations = data,
        error => console.error('Error searching destinations', error)
      );
    } else {
      this.loadDestinations();
    }
  }

  filterByCountry(): void {
    if (this.selectedCountry) {
      this.destinationService.getDestinationsByCountry(this.selectedCountry).subscribe(
        data => this.destinations = data,
        error => console.error('Error filtering by country', error)
      );
    } else {
      this.loadDestinations();
    }
  }
}
