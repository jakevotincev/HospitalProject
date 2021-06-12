import {Component, OnInit} from '@angular/core';
import {Hospital} from "../interfaces/hospital";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";

//todo: add service
@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {

  selectedHospital = new FormControl();
  filteredOptions?: Observable<Hospital[]>;

  hospitals: Hospital[] = [
    {
      "name": "поликлиника №1",
      "address": "улица1",
      "id": 1
    },
    {
      "name": "поликлиника №2",
      "address": "улица2",
      "id": 2
    },
    {
      "name": "поликлиника №3",
      "address": "улица3",
      "id": 3
    },
    {
      "name": "поликлиника №4",
      "address": "улица4",
      "id": 4
    },
    {
      "name": "хуйня",
      "address": "улица4",
      "id": 4
    }
  ];

  private _filter(value: string): Hospital[] {
    const filterValue = value.toLowerCase();

    return this.hospitals.filter(hospital => this.displayFn(hospital).toLowerCase().includes(filterValue));
  }

  displayFn(hospital: Hospital): string {
    return hospital && hospital.name ? hospital.name : '';
  }

  ngOnInit(): void {
    this.filteredOptions = this.selectedHospital.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this._filter(name) : this.hospitals.slice())
      );
  }
}
