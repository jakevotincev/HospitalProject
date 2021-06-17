import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {catchError} from "rxjs/operators";
import {Moment} from "moment";

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private url: string = 'http://localhost:8080/hospitals/';

  constructor(private http: HttpClient) {
  }

  private getUrl(doctorId?: number, hospitalId?: number, patientId?: number, date?: Moment) {
    if (!doctorId && !hospitalId && !patientId && !date) return 'http://localhost:8080/appointments';
    else if (!doctorId && !hospitalId && !date && patientId) return 'http://localhost:8080/patient/' + patientId
      + '/appointments';
    else if (doctorId && hospitalId && !patientId && !date) return this.url + hospitalId + '/doctors/' + doctorId
      + '/appointments';
    else if (date) {
      let formattedDate: string = date.format('DD') + '_' + date.format('MM') + '_' + date.format('YYYY');
      return this.url + hospitalId + '/doctors/' + doctorId + '/appointments/' + formattedDate;
    } else return this.url;
  }

  getScheduleForDay(doctorId: number, hospitalId: number, date: Moment): Observable<object> {
    return this.http.get<object>(this.getUrl(doctorId, hospitalId, undefined, date))
      .pipe(catchError(this.handleError({})));
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      console.error(error.message);
      return of(result as T);
    };
  }

}
