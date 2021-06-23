import {Component, Input} from '@angular/core';
import {Appointment} from "../interfaces/appointment";

@Component({
  selector: 'app-appointment-table',
  templateUrl: './appointment-table.component.html',
  styleUrls: ['./appointment-table.component.css']
})
export class AppointmentTableComponent {

  @Input() displayedColumns: string[] = [];

  @Input() appointments: Appointment[] = [];

}
