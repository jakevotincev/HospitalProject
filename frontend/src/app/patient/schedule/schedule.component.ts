import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {ScheduleService} from "../../services/schedule.service";
import {Schedule} from "../../interfaces/schedule";

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {

  @Input() schedules: Schedule[] = [];
  columnsToDisplay: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday']

  constructor(private scheduleService: ScheduleService) {
  }

  getSchedulesByDay(day: string): Schedule[] {
    console.log(this.schedules.length)
    return this.scheduleService.getSchedulesByDay(day, this.schedules);
  }

  ngOnChanges(changes: SimpleChanges) {
    this.schedules = changes['schedules'] ? changes['schedules'].currentValue : [];
  }

  ngOnInit(): void {
  }

}
