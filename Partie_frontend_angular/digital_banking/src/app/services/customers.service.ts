import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../model/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {

  backendHost: string="http://localhost:8083/";

  constructor(private http : HttpClient) { }

  public  getAllCustomers(): Observable<Array<Customer>>
  {
   return   this.http.get<Array<Customer>>(this.backendHost+"customers")
  }

  searchCustomers(key :string): Observable<Array<Customer>>
  {
    return   this.http.get<Array<Customer>>(this.backendHost+"customers/search?keyword="+key)
  }

  saveCustomer(customer: Customer):Observable<Customer>
  {
    return  this.http.post<Customer>(this.backendHost+"customers",customer)
  }
}
