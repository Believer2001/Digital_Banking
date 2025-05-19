import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {

  backendHost: string="http://localhost:8083/";
  constructor(private http : HttpClient) { }

  getAllCustomers()
  {
   return   this.http.get(this.backendHost+"customers")
  }

  seachCustomers(key :string)
  {
    return   this.http.get(this.backendHost+"customers")
  }
}
