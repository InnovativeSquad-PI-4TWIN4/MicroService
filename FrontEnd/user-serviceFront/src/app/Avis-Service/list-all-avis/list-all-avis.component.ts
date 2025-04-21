import { Component } from '@angular/core';
import { AvisService } from 'src/app/services/avis-service/avis.service';
import { Avis } from 'src/app/models/Avis';
@Component({
  selector: 'app-list-all-avis',
  templateUrl: './list-all-avis.component.html',
  styleUrls: ['./list-all-avis.component.css']
})
export class ListAllAvisComponent {
  avisList: Avis[] = [];
  keyword: string = ''; // Pour stocker le mot-clé de recherche
  voyageId: number | null = null; // Pour stocker l'ID du voyage
  approuve: boolean | null = null; // Pour stocker le statut d'approbation
  constructor(private avisService: AvisService) {}

  ngOnInit(): void {
    this.getAvis(); // Récupérer les avis au chargement
  }

  // getAvis(): void {
  //   this.avisService.getAllAvis().subscribe({
  //     next: (data) => {
  //       this.avisList = data; // Stocker tous les avis dans la liste
  //     },
  //     error: (err) => {
  //       console.error('Erreur lors du chargement des avis :', err);
  //     }
  //   });
  // }

  getAvis(): void {
    // Appel à la méthode de recherche avec les critères de recherche
    this.avisService.getAvisBySearch(this.keyword, this.voyageId, this.approuve).subscribe({
      next: (data) => {
        this.avisList = data; // Mettre à jour la liste des avis
      },
      error: (err) => {
        console.error('Erreur lors du chargement des avis :', err);
      }
    });
  }
  onSearch(): void {
    this.getAvis(); // Récupérer les avis en fonction des critères de recherche
  }

   // Ajouter une réaction (like/dislike)
   ajouterReaction(avisId: number, userId: number, liked: boolean): void {
    this.avisService.ajouterReaction(avisId, userId, liked).subscribe({
      next: (reaction) => {
        console.log('Réaction ajoutée avec succès !');
        this.getAvis(); // Recharger la liste après la réaction
      },
      error: (err) => {
        console.error('Erreur lors de l\'ajout de la réaction :', err);
      }
    });
  }
}
