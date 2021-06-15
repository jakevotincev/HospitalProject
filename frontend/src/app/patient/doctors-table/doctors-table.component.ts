import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {DoctorService} from "../../services/doctor.service";
import {Doctor} from "../../interfaces/doctor";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ScheduleService} from "../../services/schedule.service";
import {Schedule} from "../../interfaces/schedule";

@Component({
  selector: 'app-doctors-table',
  templateUrl: './doctors-table.component.html',
  styleUrls: ['./doctors-table.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class DoctorsTableComponent implements OnInit {

  @Input() hospitalId: number = -1;
  @Input() speciality: string = '';

  columnsToDisplay: string[] = ['name', 'scheduleButton'];
  expandedElement: Doctor | null = null;

  doctors: Doctor[] = [];
  schedules: Schedule[] = [];

  constructor(private doctorService: DoctorService, private scheduleService: ScheduleService) { }

  ngOnChanges(changes: SimpleChanges){
    this.hospitalId = changes['hospitalId'] ? changes['hospitalId'].currentValue : this.hospitalId;
    this.speciality = changes['speciality'] ? changes['speciality'].currentValue : this.speciality;
    this.getDoctors();
  }

  private getDoctors():void{
    this.doctorService.getDoctors(this.hospitalId, this.speciality).subscribe(doctors => this.doctors = doctors);
  }

  getSchedules(doctorId:number): void{
    this.scheduleService.getSchedules(this.hospitalId, doctorId).subscribe(schedules => this.schedules = schedules);
  }

  ngOnInit(): void {
    this.getDoctors();
  }

}
