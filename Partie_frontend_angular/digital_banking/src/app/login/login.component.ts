import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  formLogin! : FormGroup;


 constructor(private fb : FormBuilder,private  authService: AuthService,
             private  router: Router) {

 }
 ngOnInit()
 {
   this.formLogin=this.fb.group({
     username : this.fb.control(""),
     password : this.fb.control("")
   })
 }

  handleLogin() {
   let username = this.formLogin.value.username;
   let password = this.formLogin.value.password;
   this.authService.login(username,password).subscribe(
     {
       next :data => {
         this.authService.loadProfile(data);
         this.router.navigateByUrl("/admin");
       },
       error :err => {console.log(err)}
     }
   )
  }
}
