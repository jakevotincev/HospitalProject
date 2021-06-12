import {Component, OnInit} from '@angular/core';
import {Hospital} from "../interfaces/hospital";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {HospitalService} from "../services/hospital.service";


@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {

  selectedHospital = new FormControl();
  filteredOptions?: Observable<Hospital[]>;

  hospitals: Hospital[] = [];


  constructor(private hospitalService: HospitalService) {
  }

  private _filter(value: string): Hospital[] {
    const filterValue = value.toLowerCase();

    return this.hospitals.filter(hospital => this.displayFn(hospital).toLowerCase().includes(filterValue));
  }

  private getHospitals(): void {
    this.hospitalService.getHospitals().subscribe(hospitals => this.hospitals = hospitals);
  }

  displayFn(hospital: Hospital): string {
    return hospital && hospital.name ? hospital.name : '';
  }

  ngOnInit(): void {
    this.getHospitals()
    this.filteredOptions = this.selectedHospital.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filter(name) : this.hospitals.slice())
      );
  }
}
