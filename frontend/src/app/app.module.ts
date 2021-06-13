import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {MatSliderModule} from "@angular/material/slider";
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HeaderComponent} from './header/header.component';
import {AppRoutingModule} from './app-routing.module';
import {PatientComponent} from './patient/patient.component';
import {DoctorComponent} from './doctor/doctor.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatOptionModule} from "@angular/material/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {HttpClientModule} from "@angular/common/http";
import {HospitalSearchBarComponent} from './patient/hospital-search-bar/hospital-search-bar.component';
import { SelectSpecialityComponent } from './patient/select-speciality/select-speciality.component';
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PatientComponent,
    DoctorComponent,
    HospitalSearchBarComponent,
    SelectSpecialityComponent
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MatSliderModule,
        AppRoutingModule,
        MatAutocompleteModule,
        MatOptionModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatInputModule,
        FormsModule,
        HttpClientModule,
        MatSelectModule
    ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
