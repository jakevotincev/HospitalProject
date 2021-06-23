import {Component} from '@angular/core';
import {PatientService} from "../../services/patient.service";
import {Patient} from "../../interfaces/patient";
import {Appointment} from "../../interfaces/appointment";
import {AppointmentService} from "../../services/appointment.service";

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.css']
})
export class PatientFormComponent {

  constructor(private patientService: PatientService, private appointmentService: AppointmentService) {
  }

  patient: Patient = {
    name: '',
    middleName: '',
    surname: ''
  };

  displayedColumns: string[] = ['Date', 'Doctor', 'Place']
  appointments: Appointment[] = [];
  errorMsg: string = '';

  onSubmit(event: any): void {
    event.preventDefault();
    this.patientService.getPatientByInitials(this.patient.name, this.patient.surname, this.patient.middleName)
      .subscribe(patient => {
        this.errorMsg = '';
        this.patient = patient;
        if (patient.id)
          this.appointmentService.getAppointmentsByPatientId(patient.id).subscribe(appointments => this.appointments = appointments);
      }, () => {
        this.appointments = [];
        this.errorMsg = 'Записи пациента ' + this.patient.surname + ' ' + this.patient.name + ' ' + this.patient.middleName + ' не найдены.';
      });
  }
}
