import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Patient} from "../interfaces/patient";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) {
  }

  // private url: string = 'https://azure-hospital-hospital-service.azuremicroservices.io/patients'
  private url: string = 'http://localhost:8080/patients'

  savePatient(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(this.url, patient)
  }

  getPatientByInitials(name: string, surname: string, middleName: string): Observable<Patient> {
    return this.http.get<Patient>(this.url, {
      params: {
        name: name,
        surname: surname,
        middleName: middleName
      }
    })
  }

  // private handleError<T>(result?: T) {
  //   return (error: any): Observable<T> => {
  //     console.error(error.message);
  //     return of(result as T);
  //   };
  // }
}
