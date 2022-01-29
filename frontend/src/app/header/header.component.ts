import {Component, Input} from '@angular/core';
import {OAuthService} from "angular-oauth2-oidc";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  template: `
    <div class="header">
      <button mat-raised-button color="primary" (click)="logOut()" *ngIf="logged">Выйти</button>
      <h1>Hospital service by Votintsev Evgenii</h1>
      <div>
        <nav>
          <a routerLink="/patients">Для пациентов</a>
          <a routerLink="/doctors">Для врачей</a>
        </nav>
      </div>

      <hr>

    </div>`,
  styles: [`
    .header {
      text-align: center;
      margin: 10px;
    }

    button {
      top: 1.8em;
      right: 0.6em;
      position: absolute;
    }

    a {
      margin: 5px;
    }`]
})
export class HeaderComponent {
  constructor(private oAuthService: OAuthService, private router: Router) {

  }

  @Input() logged: boolean = false;


  logOut() {
    this.oAuthService.logOut();
  }
}
