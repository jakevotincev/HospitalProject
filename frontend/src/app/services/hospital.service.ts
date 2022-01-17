import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Hospital} from "../interfaces/hospital";

@Injectable({
  providedIn: 'root'
})
export class HospitalService {

  private url: string = 'https://azure-hospital-hospital-service.azuremicroservices.io/hospitals'

  constructor(private http: HttpClient) {
  }

  getHospitals(): Observable<Hospital[]> {
    return this.http.get<Hospital[]>(this.url);
  }
}
