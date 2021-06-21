import {Component, Input, OnInit} from '@angular/core';
import {AppointmentService} from "../../../services/appointment.service";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {
  MAT_MOMENT_DATE_ADAPTER_OPTIONS,
  MAT_MOMENT_DATE_FORMATS,
  MomentDateAdapter,
} from '@angular/material-moment-adapter';
import * as moment from 'moment';
import {Moment} from 'moment';
import 'moment/locale/ru';
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "./dialog/dialog.component";
import {Hospital} from "../../../interfaces/hospital";
import {Doctor} from "../../../interfaces/doctor";
import {PatientService} from "../../../services/patient.service";
import {DaySchedule} from "../../../interfaces/day-schedule";

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
  selectedDate: Moment = moment(new Date());
  dateStart: Moment = moment(new Date());
  minDate: Moment = moment(new Date());
  maxDate: Moment = moment(new Date(Date.now() + 3600 * 24 * 1000 * 31));

  @Input() hospital: Hospital = {
    id: -1,
    name: '',
    address: ''
  };
  @Input() doctor: Doctor = {
    name: '',
    surname: '',
    middleName: '',
    speciality: '',
    hospitals: [],
    id: -1
  };
  schedule: DaySchedule = {};
  selectedTime: string = '';


  constructor(private appointmentService: AppointmentService, private patientService: PatientService, private dialog: MatDialog) {
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '400px',
      height: 'auto',
      data: {
        middleName: '',
        name: '',
        surname: '',
        age: '',
        comment: '',
        doSave: false,
        selectedDate: this.selectedDate,
        selectedTime: this.selectedTime,
        doctor: this.doctor,
        hospital: this.hospital
      }
    });

    dialogRef.afterClosed().subscribe(() => this.getSchedule());

  }

  private getSchedule() {
    if (this.doctor.id && this.hospital.id)
      this.appointmentService.getScheduleForDay(this.doctor.id, this.hospital.id, this.selectedDate)
        .subscribe(schedule => {
          for (const [key, value] of Object.entries(schedule)) {
            if (!value) delete schedule[key]
          }
          this.schedule = schedule
        });
  }

  ngOnInit(): void {
    this.getSchedule();
  }

  onSelectDay(event: any): void {
    this.selectedDate = event;
    this.getSchedule();

  }

  onSelectTime(event: any): void {
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
