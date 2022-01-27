import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Doctor} from "../interfaces/doctor";

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private url: string = 'https://azure-hospital-hospital-service.azuremicroservices.io/hospitals/';
  // private url: string = 'http://localhost:8080/hospitals/';

  constructor(private http: HttpClient) {
  }

  private getUrl(hospitalId: number, speciality?: string): string {
    if (hospitalId && !speciality) return this.url + hospitalId + '/doctors/specialities';
    else return this.url + hospitalId + '/doctors/' + speciality;
  }

  getSpecialities(hospitalId: number): Observable<string[]> {
    return this.http.get<string[]>(this.getUrl(hospitalId));
  }

  getDoctors(hospitalId: number, speciality: string): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(this.getUrl(hospitalId, speciality));
  }

  getByInitials(name:string, surname:string, middleName: string){
    return this.http.get<Doctor>('https://azure-hospital-hospital-service.azuremicroservices.io/doctors', {
      params:{
        name: name.trim(),
        surname: surname.trim(),
        middleName: middleName.trim()
      }
    })
  }
}
