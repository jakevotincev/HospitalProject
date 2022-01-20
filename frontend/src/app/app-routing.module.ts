import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {PatientComponent} from "./patient/patient.component";
import {DoctorComponent} from "./doctor/doctor.component";


const routes: Routes = [
  {path: 'patients', component: PatientComponent},
  {path: 'doctors', component: DoctorComponent},
  {path: '', redirectTo: '/patients', pathMatch: 'full'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
