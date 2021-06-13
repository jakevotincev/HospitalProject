import {Component, Input, OnInit, Output, SimpleChanges, EventEmitter} from '@angular/core';
import {DoctorService} from "../../services/doctor.service";

@Component({
  selector: 'app-select-speciality',
  templateUrl: './select-speciality.component.html',
  styleUrls: ['./select-speciality.component.css']
})
export class SelectSpecialityComponent implements OnInit {

  @Input() hospitalId: number = -1;
  @Output() specialityEvent = new EventEmitter<string>();

  specialities: string[] = [];

  selectedSpeciality: string = '';

  constructor(private doctorService: DoctorService) {
  }

  ngOnChanges(changes: SimpleChanges){
    this.hospitalId = changes['hospitalId'].currentValue;
    this.getSpecialities();
  }

  selectSpeciality(speciality: string){
    this.specialityEvent.emit(speciality)
  }

  private getSpecialities(): void {
    this.doctorService.getSpecialities(this.hospitalId).subscribe(specialities => this.specialities = specialities);
  }

  ngOnInit(): void {
    this.getSpecialities()
  }

}
