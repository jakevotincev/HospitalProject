import {Component} from '@angular/core';
import {OAuthService} from "angular-oauth2-oidc";
import {authConfig} from "../auth.config";
import {Router} from "@angular/router";

//todo: redirect after logout
//todo: load user profile
//todo: move button to header +-
//todo: add redis ro backend
//todo: move patient form to doctor component
@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css']
})
export class DoctorComponent {
  constructor(private oAuthService: OAuthService, private router: Router) {

  }

  logged:boolean = false;

  ngOnInit(): void {
    this.oAuthService.configure(authConfig);
    this.oAuthService.setupAutomaticSilentRefresh();
    this.oAuthService.loadDiscoveryDocumentAndLogin().then(()=>{
      if (this.oAuthService.getAccessToken()!==null) this.logged = true;
    });
    this.oAuthService.setupAutomaticSilentRefresh();
  }
}
