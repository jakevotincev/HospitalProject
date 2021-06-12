import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {MatSliderModule} from "@angular/material/slider";

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HeaderComponent } from './header/header.component';
import { AppRoutingModule } from './app-routing.module';
import { PatientComponent } from './patient/patient.component';
import { DoctorComponent } from './doctor/doctor.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PatientComponent,
    DoctorComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatSliderModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
