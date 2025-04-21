import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from './auth/signin/signin.component';
import { SignupComponent } from './auth/signup/signup.component';
import { HomeComponent } from './home/home.component';
import { AddAvisComponent } from './Avis-Service/add-avis/add-avis.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { ListAllAvisComponent } from './Avis-Service/list-all-avis/list-all-avis.component';
import { EditAvisComponent } from './Avis-Service/edit-avis/edit-avis.component';

const routes: Routes = [
  { path: 'signin', component: SigninComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'home', component: HomeComponent },
  { path: 'avis', component: AddAvisComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'list-all-avis', component: ListAllAvisComponent },
  { path: 'edit-avis/:id', component: EditAvisComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
