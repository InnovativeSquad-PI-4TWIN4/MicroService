import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from './auth/signin/signin.component';
import { SignupComponent } from './auth/signup/signup.component';
import { HomeComponent } from './home/home.component';
import { AddAvisComponent } from './Avis-Service/add-avis/add-avis.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { AddReservationComponent } from './Reservation-Service/add-reservation/add-reservation.component';
import { ListReservationsComponent } from './Reservation-Service/list-reservations/list-reservations.component';
import { EditReservationComponent } from './Reservation-Service/edit-reservation/edit-reservation.component';
import { ProfileComponent } from './auth/profile/profile.component';
import { StatisticsComponent } from './Reservation-Service/statistics/statistics.component';
import { RecommendationsComponent } from './Reservation-Service/recommendations/recommendations.component';
const routes: Routes = [
  { path: 'signin', component: SigninComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'home', component: HomeComponent },
  { path: 'avis', component: AddAvisComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reservation/add', component: AddReservationComponent },
  { path: 'reservation/list', component: ListReservationsComponent },
  { path: 'reservation/edit/:id', component: EditReservationComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'reservation/statistics', component: StatisticsComponent },
  { path: 'recommendations', component: RecommendationsComponent },
  { path: '', redirectTo: 'signin', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
