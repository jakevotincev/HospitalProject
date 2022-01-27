import {Component} from '@angular/core';
import {Hospital} from "../interfaces/hospital";
import {OAuthService} from "angular-oauth2-oidc";
import {Router} from "@angular/router";


@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent {
  constructor(private oAuthService: OAuthService, private router: Router) {

  }
  // ngOnInit(): void {
  //   this.oAuthService.configure(authConfig);
  //   this.oAuthService.setupAutomaticSilentRefresh();
  //   console.log(this.oAuthService.getAccessToken());
  //   this.oAuthService.loadDiscoveryDocumentAndLogin().then(()=>{
  //     // if (this.oAuthService.getAccessToken()!==null) this.logged = true;
  //   });
  //
  // }
  hospital?: Hospital;
  speciality?: string;

  selectHospital(hospital: Hospital): void{
    this.hospital = hospital;
    this.speciality = '';
  }

  selectSpeciality(speciality: string): void{
    this.speciality = speciality
  }

}
