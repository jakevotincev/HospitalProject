import {Component} from '@angular/core';
import {Hospital} from "../interfaces/hospital";
import {View} from "./view";


@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent {

  view: View = View.SaveAppointment;
  hospital?: Hospital;
  speciality?: string;

  selectHospital(hospital: Hospital): void{
    this.hospital = hospital;
    this.speciality = '';
  }

  selectSpeciality(speciality: string): void{
    this.speciality = speciality
  }

  changeView(view:number){
    this.view = view;
  }
}
