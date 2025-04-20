import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { SigninComponent } from './auth/signin/signin.component';
import { SignupComponent } from './auth/signup/signup.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { AddAvisComponent } from './Avis-Service/add-avis/add-avis.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { AddReservationComponent } from './Reservation-Service/add-reservation/add-reservation.component';
import { ListReservationsComponent } from './Reservation-Service/list-reservations/list-reservations.component';
import { EditReservationComponent } from './Reservation-Service/edit-reservation/edit-reservation.component';
import { ProfileComponent } from './auth/profile/profile.component';
import { StatisticsComponent } from './Reservation-Service/statistics/statistics.component';
import { RecommendationsComponent } from './Reservation-Service/recommendations/recommendations.component';
import { ReservationOptionsComponent } from './Reservation-Service/reservation-options/reservation-options.component';
import { AgenceListComponent } from './Agence-Service/agence-list/agence-list.component';
import { AgenceFormComponent } from './Agence-Service/agence-form/agence-form.component';
import { AgenceDetailsComponent } from './Agence-Service/agence-details/agence-details.component';
import { AgenceSearchComponent } from './Agence-Service/agence-search/agence-search.component';
import { AgenceExportComponent } from './Agence-Service/agence-export/agence-export.component';
import { AgenceEmailComponent } from './Agence-Service/agence-email/agence-email.component';
import { AgenceResponsableComponent } from './Agence-Service/agence-responsable/agence-responsable.component';
@NgModule({
  declarations: [
    AppComponent,
    SigninComponent,
    SignupComponent,
    FooterComponent,
    NavBarComponent,
    HomeComponent,
    AddAvisComponent,
    ForgotPasswordComponent,
    AddReservationComponent,
    ListReservationsComponent,
    EditReservationComponent,
    ProfileComponent,
    StatisticsComponent,
    RecommendationsComponent,
    ReservationOptionsComponent,
    AgenceListComponent,
    AgenceFormComponent,
    AgenceDetailsComponent,
    AgenceSearchComponent,
    AgenceExportComponent,
    AgenceEmailComponent,
    AgenceResponsableComponent
    
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
