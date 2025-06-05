import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

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
 constructor(private fb : FormBuilder) {

 }
 ngOnInit()
 {
   this.formLogin=this.fb.group({
     username : this.fb.control(""),
     password : this.fb.control("")
   })
 }

  handleLogin() {
    console.log(this.formLogin.value);
  }
}
