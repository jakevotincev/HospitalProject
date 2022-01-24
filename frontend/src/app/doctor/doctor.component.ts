import {Component} from '@angular/core';
import {OAuthService} from "angular-oauth2-oidc";
import {authConfig} from "../auth.config";
import {Router} from "@angular/router";
import {View} from "./view";

//todo: redirect after logout
//todo: load user profile
//todo: move button to header +-
//todo: add redis ro backend
@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css']
})
export class DoctorComponent {
  constructor(private oAuthService: OAuthService, private router: Router) {

  }

  view: View = View.GetDoctorAppointments;
  logged:boolean = false;

  ngOnInit(): void {
    this.oAuthService.configure(authConfig);
    this.oAuthService.setupAutomaticSilentRefresh();
    this.oAuthService.loadDiscoveryDocumentAndLogin().then(()=>{
      if (this.oAuthService.getAccessToken()!==null) this.logged = true;
    });
    this.oAuthService.setupAutomaticSilentRefresh();
  }

  changeView(view:number){
    this.view = view;
  }
}
