import {Component} from '@angular/core';
import {AppointmentService} from "../../services/appointment.service";
import {Appointment} from "../../interfaces/appointment";
import {Doctor} from "../../interfaces/doctor";
import {DoctorService} from "../../services/doctor.service";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {
  MAT_MOMENT_DATE_ADAPTER_OPTIONS,
  MAT_MOMENT_DATE_FORMATS,
  MomentDateAdapter
} from "@angular/material-moment-adapter";
import 'moment/locale/ru';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-doctor-form',
  templateUrl: './doctor-form.component.html',
  styleUrls: ['./doctor-form.component.css'],
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
export class DoctorFormComponent {

  constructor(private doctorService: DoctorService, private appointmentService: AppointmentService) {
  }

  doctor: Doctor = {
    name: '',
    middleName: '',
    surname: ''
  };

  displayedColumns: string[] = ['Date', 'Patient', 'Place', 'Comment'];

  appointments: Appointment[] = [];
  doctorErrorMsg: string = '';
  appointmentErrorMsg: string = '';
  showDatepicker: boolean = false;

  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl()
  });

  onClick() {
    if (this.doctor.id)
      this.appointmentService.getAppointmentsByDoctorIdAndDuration(this.doctor.id, this.range.value.start, this.range.value.end)
        .subscribe(appointments => {
          this.appointments = appointments;
          this.appointmentErrorMsg = '';
          console.log(appointments);
          if (appointments.length === 0) {
            this.appointments = [];
            this.appointmentErrorMsg = 'Записи врача ' + this.doctor.surname + ' ' + this.doctor.name + ' '
              + this.doctor.middleName + ' за этот период не найдены.';
          }
        });
  }

  onSubmit(event: any): void {
    event.preventDefault();
    this.doctorService.getByInitials(this.doctor.name, this.doctor.surname, this.doctor.middleName)
      .subscribe(doctor => {
        this.doctorErrorMsg = '';
        this.doctor = doctor;
        this.showDatepicker = true;
      }, () => {
        this.showDatepicker = false;
        this.doctorErrorMsg = 'Врач ' + this.doctor.surname + ' ' + this.doctor.name + ' ' + this.doctor.middleName + ' не найден.';
      });
  }

}
