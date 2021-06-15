import {Component, Input, SimpleChanges} from '@angular/core';
import {ScheduleService} from "../../services/schedule.service";
import {Schedule} from "../../interfaces/schedule";

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent {

  @Input() schedules: Schedule[] = [];
  columnsToDisplay: string[] = ['Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'];

  constructor(private scheduleService: ScheduleService) {
  }

  getSchedulesByDay(day: string): Schedule[] {
    return this.scheduleService.getSchedulesByDay(day, this.schedules);
  }

  ngOnChanges(changes: SimpleChanges) {
    this.schedules = changes['schedules'] ? changes['schedules'].currentValue : [];
  }

}
