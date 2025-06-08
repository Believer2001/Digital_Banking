import { Component } from '@angular/core';
import {NavComponent} from '../nav/nav.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-admin-template',
  imports: [
    NavComponent,
    RouterOutlet
  ],
  templateUrl: './admin-template.component.html',
  styleUrl: './admin-template.component.css'
})
export class AdminTemplateComponent {

}
