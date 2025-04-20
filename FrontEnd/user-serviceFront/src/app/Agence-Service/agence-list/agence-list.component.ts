import { Component, OnInit } from '@angular/core';
import { AgenceService } from 'src/app/services/agence-service/agence.service';
import { Agence } from '../../models/agence';

@Component({
  selector: 'app-agence-list',
  templateUrl: './agence-list.component.html'
})
export class AgenceListComponent implements OnInit {
  agences: Agence[] = [];

  constructor(private agenceService: AgenceService) {}

  ngOnInit(): void {
    this.loadAgences();
  }

  loadAgences(): void {
    this.agenceService.getAllAgences().subscribe(data => {
      this.agences = data;
    });
  }

  deleteAgence(id: string): void {
    if (confirm('Are you sure you want to delete this agency?')) {
      this.agenceService.deleteAgence(id).subscribe(() => {
        this.loadAgences();
      });
    }
  }

  toggleStatus(id: string, active: boolean): void {
    this.agenceService.toggleActiveStatus(id, !active).subscribe(() => {
      this.loadAgences();
    });
  }
}