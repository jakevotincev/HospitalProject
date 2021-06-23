import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {catchError} from "rxjs/operators";
import {Moment} from "moment";
import {Appointment} from "../interfaces/appointment";
import {DaySchedule} from "../interfaces/day-schedule";

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private url: string = 'http://localhost:8080/hospitals/';

  constructor(private http: HttpClient) {
  }

  private getUrl(doctorId?: number, hospitalId?: number, patientId?: number, date?: Moment, secondDate?: Moment) {
    if (!doctorId && !hospitalId && !patientId && !date) return 'http://localhost:8080/appointments';
    else if(doctorId&&!hospitalId&&!patientId&&date&&secondDate) return 'http://localhost:8080/doctors/' + doctorId + '/appointments/'
      + date.format('DD_MM_yyyy HH:mm:ss') + '/' + secondDate.format('DD_MM_yyyy HH:mm:ss');
    else if (!doctorId && !hospitalId && !date && patientId) return 'http://localhost:8080/patients/' + patientId
      + '/appointments';
    else if (doctorId && hospitalId && !patientId && !date) return this.url + hospitalId + '/doctors/' + doctorId
      + '/appointments';
    else if (date) {
      let formattedDate: string = date.format('DD') + '_' + date.format('MM') + '_' + date.format('YYYY');
      return this.url + hospitalId + '/doctors/' + doctorId + '/appointments/' + formattedDate;
    } else return this.url;
  }

  getScheduleForDay(doctorId: number, hospitalId: number, date: Moment): Observable<DaySchedule> {
    return this.http.get<DaySchedule>(this.getUrl(doctorId, hospitalId, undefined, date))
      .pipe(catchError(this.handleError({})));
  }

  saveAppointment(appointment: Appointment) {
    return this.http.post<Appointment>(this.getUrl(), appointment);
  }

  getAppointmentsByPatientId(patientId: number) {
    return this.http.get<Appointment[]>(this.getUrl(undefined, undefined, patientId))
  }

  getAppointmentsByDoctorIdAndDuration(doctorId: number, first: Moment, second: Moment){
    return this.http.get<Appointment[]>(this.getUrl(doctorId, undefined, undefined, first, second))
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      console.error(error.message);
      return of(result as T);
    };
  }

}
