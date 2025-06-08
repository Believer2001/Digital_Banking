import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {jwtDecode} from 'jwt-decode';
import {AuthMyJwtPayload} from '../model/auth.MyJwtPayload';


@Injectable({
  providedIn: 'root'
})



export class AuthService {
  backendHost: string="http://localhost:8083/";
  roles :any;
  username: any;
  accessToken!: string;
  isAuthenticated :boolean =false;


  constructor(private http:HttpClient) { }


  public  login(username: string,password:string)
  {
    let options= {
      headers :new HttpHeaders().set("Content-Type","application/x-www-form-urlencoded")
    }
    let params =new HttpParams()
      .set("username",username)
      .set("password",password);

    return this.http.post(this.backendHost+"auth/login",params,options)
  }

  loadProfile(data: any) {
    this.isAuthenticated =true;
    this.accessToken =data['access_token'];
    let decodeJwt = jwtDecode<AuthMyJwtPayload>(this.accessToken);
    this.username=decodeJwt.sub;
    this.roles =decodeJwt.scope;

  }




}
