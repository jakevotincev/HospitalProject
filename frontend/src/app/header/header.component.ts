import {Component} from '@angular/core';

@Component({
  selector: 'app-header',
  template: `
    <div class="header">
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

    a {
      margin: 5px;
    }`]
})
export class HeaderComponent {
}
