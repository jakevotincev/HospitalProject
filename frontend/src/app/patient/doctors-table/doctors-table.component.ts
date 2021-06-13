import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {DoctorService} from "../../services/doctor.service";
import {Doctor} from "../../interfaces/doctor";

@Component({
  selector: 'app-doctors-table',
  templateUrl: './doctors-table.component.html',
  styleUrls: ['./doctors-table.component.css']
})
export class DoctorsTableComponent implements OnInit {

  @Input() hospitalId: number = -1;
  @Input() speciality: string = '';

  columnsToDisplay: string[] = ['surname', 'name', 'middleName'];

  doctors: Doctor[] = [];

  constructor(private doctorService: DoctorService) { }

  ngOnChanges(changes: SimpleChanges){
    this.hospitalId = changes['hospitalId'] ? changes['hospitalId'].currentValue : this.hospitalId;
    this.speciality = changes['speciality'] ? changes['speciality'].currentValue : this.speciality;
    this.getDoctors();
  }

  private getDoctors():void{
    this.doctorService.getDoctors(this.hospitalId, this.speciality).subscribe(doctors => this.doctors = doctors);
  }

  ngOnInit(): void {
    this.getDoctors();
  }

}
