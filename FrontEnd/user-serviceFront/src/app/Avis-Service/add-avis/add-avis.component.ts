import { Component } from '@angular/core';
import { AvisService } from 'src/app/services/avis-service/avis.service';
import { Avis } from 'src/app/models/Avis';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
@Component({
  selector: 'app-add-avis',
  templateUrl: './add-avis.component.html',
  styleUrls: ['./add-avis.component.css']
})
export class AddAvisComponent {
  avis: Avis = {
    utilisateurId: 0, // sera assigné automatiquement
    voyageId: 0,
    note: 0,
    commentaire: ''
  };
  
  stars: number[] = [1, 2, 3, 4, 5];
  avisList: Avis[] = [];


  constructor(
    private avisService: AvisService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getCurrentUserId(); // ou le récupérer depuis localStorage par ex.
    this.avis.utilisateurId = userId;
    this.getAvis();
  }
 
  
  onSubmit(): void {
    this.avisService.createAvis(this.avis).subscribe({
      next: () => {
        console.log('Avis ajouté avec succès !');
        this.router.navigate(['/avis']);
      },
      error: (err) => {
        console.error('Erreur lors de l\'ajout de l\'avis :', err);
        alert('Erreur lors de l\'ajout.');
      }
    });
  }


setRating(value: number): void {
  this.avis.note = value;
}



getAvis(): void {
  this.avisService.getAllAvis().subscribe({
    next: (data) => {
      this.avisList = data;
    },
    error: (err) => {
      console.error('Erreur lors du chargement des avis :', err);
    }
  });
}
}
