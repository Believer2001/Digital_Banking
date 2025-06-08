import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-nav',
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {
 constructor(public authService: AuthService,private router:Router) {
 }
  handleLogOut() {
 this.authService.logout();
 this.router.navigateByUrl("/login");
  }
}
