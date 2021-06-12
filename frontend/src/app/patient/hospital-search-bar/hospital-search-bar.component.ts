import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {Hospital} from "../../interfaces/hospital";
import {HospitalService} from "../../services/hospital.service";
import {map, startWith} from "rxjs/operators";

@Component({
  selector: 'app-hospital-search-bar',
  templateUrl: './hospital-search-bar.component.html',
  styleUrls: ['./hospital-search-bar.component.css']
})
export class HospitalSearchBarComponent implements OnInit {

  @Output() hospitalEvent = new EventEmitter<Hospital>()

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

  selectHospital(hospital: Hospital): void {
    this.hospitalEvent.emit(hospital);
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
