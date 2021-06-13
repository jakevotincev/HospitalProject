import {Component} from '@angular/core';
import {Hospital} from "../interfaces/hospital";


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
  }

  selectSpeciality(speciality: string): void{
    this.speciality = speciality
  }
}
