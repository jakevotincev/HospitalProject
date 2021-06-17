import {Component, Input, OnInit} from '@angular/core';
import {AppointmentService} from "../../services/appointment.service";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {
  MAT_MOMENT_DATE_FORMATS,
  MomentDateAdapter,
  MAT_MOMENT_DATE_ADAPTER_OPTIONS,
} from '@angular/material-moment-adapter';
import * as moment from 'moment';
import 'moment/locale/ru';
import {Moment} from "moment";

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css'],
  providers: [
    {
      provide: MAT_DATE_LOCALE,
      useValue: 'ru'
    },
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
  ],
})
export class AppointmentComponent implements OnInit {
  selectedDate: any = moment(new Date());
  dateStart: Moment = moment(new Date());
  minDate: Moment = moment(new Date());
  maxDate: Moment = moment(new Date(Date.now() + 3600 * 24 * 1000 * 31));

  @Input() hospitalId: number = -1;
  @Input() doctorId: number = -1;
  schedule: object = {};
  selectedTime: string = '';

  constructor(private appointmentService: AppointmentService) {
  }

  ngOnInit(): void {
    this.appointmentService.getScheduleForDay(this.doctorId, this.hospitalId, this.selectedDate)
      .subscribe(schedule => this.schedule = schedule);
  }

  onSelectDay(event: any): void {
    this.selectedDate = event;
    this.appointmentService.getScheduleForDay(this.doctorId, this.hospitalId, this.selectedDate)
      .subscribe(schedule => this.schedule = schedule);

  }

  onSelectTime(event: any): void{
    this.selectedTime = event.option.value.key;
  }

  dateFilter(d: Moment): boolean {
    let day = d.day();
    return day !== 0 && day !== 6;
  }

  isEmpty(obj: object): boolean {
    return Object.keys(obj).length === 0;
  }
}
