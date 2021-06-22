import {Component, Input, OnInit} from '@angular/core';
import {Appointment} from "../interfaces/appointment";

@Component({
  selector: 'app-appointment-table',
  templateUrl: './appointment-table.component.html',
  styleUrls: ['./appointment-table.component.css']
})
export class AppointmentTableComponent implements OnInit {

  displayedColumns: string[] = ['Date', 'Doctor', 'Place'];

  @Input()appointments: Appointment[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
