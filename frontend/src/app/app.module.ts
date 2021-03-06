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
import {MatNativeDateModule, MatOptionModule} from "@angular/material/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {HttpClientModule} from "@angular/common/http";
import {HospitalSearchBarComponent} from './patient/hospital-search-bar/hospital-search-bar.component';
import { SelectSpecialityComponent } from './patient/select-speciality/select-speciality.component';
import {MatSelectModule} from "@angular/material/select";
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { DoctorsTableComponent } from './patient/doctors-table/doctors-table.component';
import {MatButtonModule} from "@angular/material/button";
import { ScheduleComponent } from './patient/doctors-table/schedule/schedule.component';
import { AppointmentComponent } from './patient/doctors-table/appointment/appointment.component';
import {MatCardModule} from "@angular/material/card";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatListModule} from "@angular/material/list";
import {ScrollingModule} from "@angular/cdk/scrolling";
import { DialogComponent } from './patient/doctors-table/appointment/dialog/dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import { PatientFormComponent } from './patient/patient-form/patient-form.component';
import { AppointmentTableComponent } from './appointment-table/appointment-table.component';
import { DoctorFormComponent } from './doctor/doctor-form/doctor-form.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PatientComponent,
    DoctorComponent,
    HospitalSearchBarComponent,
    SelectSpecialityComponent,
    DoctorsTableComponent,
    ScheduleComponent,
    AppointmentComponent,
    DialogComponent,
    PatientFormComponent,
    AppointmentTableComponent,
    DoctorFormComponent
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
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatListModule,
    ScrollingModule,
    MatDialogModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
