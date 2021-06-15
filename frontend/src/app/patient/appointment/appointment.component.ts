import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {
  selectedDate: any = null;
  dateStart: Date = new Date();
  minDate: Date = this.dateStart;
  maxDate: Date = new Date(Date.now() + 3600 * 24 * 1000 * 31);

  constructor() {
  }

  ngOnInit(): void {
  }

  onSelect(event: any): void {
    this.selectedDate = event;
  }

  dateFilter(d:Date): boolean{
    let day = d.getDay();
    return day !== 0 && day !== 6 ;
  }
}
