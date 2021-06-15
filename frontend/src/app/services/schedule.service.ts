import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Schedule} from "../interfaces/schedule";

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  private url: string = 'http://localhost:8080/hospitals/';

  constructor(private http: HttpClient) {
  }

  private getUrl(hospitalId: number, doctorId: number) {
    return this.url + hospitalId + '/doctors/' + doctorId + '/schedules';
  }

  getSchedules(hospitalId: number, doctorId: number) {
    return this.http.get<Schedule[]>(this.getUrl(hospitalId, doctorId));
  }

  getSchedulesByDay(day: string, schedules: Schedule[]): Schedule[]{
    return schedules.filter(schedule => schedule.dayOfWeek===day);
  }
}
