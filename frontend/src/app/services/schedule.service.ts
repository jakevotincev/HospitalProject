import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Schedule} from "../interfaces/schedule";

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  // private url: string = 'https://azure-hospital-hospital-service.azuremicroservices.io/hospitals/';
  private url: string = 'http://localhost:8080/hospitals/';

  constructor(private http: HttpClient) {
  }

  private getUrl(hospitalId: number, doctorId: number) {
    return this.url + hospitalId + '/doctors/' + doctorId + '/schedules';
  }

  getSchedules(hospitalId: number, doctorId: number) {
    return this.http.get<Schedule[]>(this.getUrl(hospitalId, doctorId));
  }

  convertDaysOfWeek(schedules: Schedule[]): Schedule[] {
    return schedules.map(schedule => {
      switch (schedule.dayOfWeek) {
        case 'MONDAY': {
          schedule.dayOfWeek = 'Пн';
          return schedule;
        }
        case 'TUESDAY': {
          schedule.dayOfWeek = 'Вт';
          return schedule
        }
        case 'WEDNESDAY': {
          schedule.dayOfWeek = 'Ср';
          return schedule;
        }
        case 'THURSDAY': {
          schedule.dayOfWeek = 'Чт';
          return schedule;
        }
        case 'FRIDAY': {
          schedule.dayOfWeek = 'Пт';
          return schedule;
        }
        case 'SATURDAY': {
          schedule.dayOfWeek = 'Сб';
          return schedule;
        }
        default:
          return schedule
      }
    })
  }

  getSchedulesByDay(day: string, schedules: Schedule[]): Schedule[] {
    return schedules.filter(schedule => schedule.dayOfWeek === day);
  }
}
