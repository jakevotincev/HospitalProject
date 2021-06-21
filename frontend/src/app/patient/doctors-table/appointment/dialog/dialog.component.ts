import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import * as moment from "moment";
import {Appointment} from "../../../../interfaces/appointment";
import {PatientService} from "../../../../services/patient.service";
import {Patient} from "../../../../interfaces/patient";
import {AppointmentService} from "../../../../services/appointment.service";

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent {

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private patientService: PatientService, private appointmentService: AppointmentService) {
  }

  patient?: Patient;
  comment: string = '';
  appointmentData?: Appointment;

  onNoClick(): void {
    this.dialogRef.close();
  }

  onClick() {
    this.comment = this.data.comment;
    this.patientService.getPatientByInitials(this.data.name, this.data.surname, this.data.middleName).subscribe(patient => {
      this.patient = patient;
      this.saveAppointment();
    }, () => {
      this.patient = {
        surname: this.data.surname,
        middleName: this.data.middleName,
        name: this.data.name,
        age: this.data.age,
      };
      this.patientService.savePatient(this.patient).subscribe(patient => {
        this.patient = patient;
        this.saveAppointment();
      });
    })
  }


  private saveAppointment() {
    let time: string = this.data.selectedDate.format('DD_MM_YYYY ') + moment(this.data.selectedTime, 'HH:mm')
      .format('HH:mm:ss');
    let appointment: Appointment = {
      doctor: this.data.doctor,
      patient: this.patient,
      hospital: this.data.hospital,
      comment: this.comment,
      date: time
    }
    this.appointmentService.saveAppointment(appointment).subscribe(appointment => {
      this.appointmentData = appointment;
    });
  }


}
