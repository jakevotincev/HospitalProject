import {Component} from '@angular/core';
import {Hospital} from "../interfaces/hospital";
import {View} from "../doctor/view";


@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent {

  hospital?: Hospital;
  speciality?: string;

  selectHospital(hospital: Hospital): void{
    this.hospital = hospital;
    this.speciality = '';
  }

  selectSpeciality(speciality: string): void{
    this.speciality = speciality
  }

}
