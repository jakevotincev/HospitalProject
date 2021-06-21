import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {DoctorService} from "../../services/doctor.service";
import {Doctor} from "../../interfaces/doctor";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ScheduleService} from "../../services/schedule.service";
import {Schedule} from "../../interfaces/schedule";
import {Hospital} from "../../interfaces/hospital";

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

  @Input() hospital: Hospital = {
    id: -1,
    name: '',
    address: '',
  };
  @Input() speciality: string = '';

  columnsToDisplay: string[] = ['name', 'scheduleButtons'];
  expandedSchedule: Doctor | null = null;
  expandedCalendar: Doctor | null = null;

  doctors: Doctor[] = [];
  schedules: Schedule[] = [];

  constructor(private doctorService: DoctorService, private scheduleService: ScheduleService) {
  }

  ngOnChanges(changes: SimpleChanges) {
    this.hospital = changes['hospital'] ? changes['hospital'].currentValue : this.hospital;
    this.speciality = changes['speciality'] ? changes['speciality'].currentValue : this.speciality;
    this.getDoctors();
  }

  private getDoctors(): void {
    this.doctorService.getDoctors(this.hospital.id, this.speciality).subscribe(doctors => this.doctors = doctors);
  }

  getSchedules(doctorId: number): void {
    this.scheduleService.getSchedules(this.hospital.id, doctorId).subscribe(schedules => this.schedules = this.scheduleService.convertDaysOfWeek(schedules));
  }

  ngOnInit(): void {
    this.getDoctors();
  }

}
