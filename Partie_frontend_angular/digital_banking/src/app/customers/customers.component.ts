
import {Component, OnInit} from '@angular/core';
import {CustomersService} from '../services/customers.service';
import {AsyncPipe, JsonPipe, NgForOf, NgIf} from '@angular/common';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {catchError, map, Observable, throwError} from 'rxjs';
import {Customer} from '../model/customer.model';
import {resolve} from '@angular/compiler-cli';

@Component({
  selector: 'app-customers',
  imports: [
    NgIf,
    ReactiveFormsModule,
    JsonPipe,
    AsyncPipe,
    NgForOf


  ],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements  OnInit{

  errMessage!: string;
  customers!: Observable<Array<Customer>>;
  searchFormGroup!: FormGroup;
  constructor(private customersService:CustomersService,private  fb :FormBuilder) {
  }
  ngOnInit() {

    this.searchFormGroup =this.fb.group({keyword :this.fb.control("")});
    this.getAllCustomers()
  }


 getAllCustomers()
 {
  this.customers= this.customersService.getAllCustomers().pipe(
    catchError(err => {
      this.errMessage=err;
      return throwError(err)
    })
  )


 }

  handleSearchCustomer() {
 let kw = this.searchFormGroup?.value.keyword
    this.customers =this.customersService.searchCustomers(kw).pipe(
      catchError(err => {
        this.errMessage=err;
        return  throwError(err);
      })
    )


  }

  protected readonly FormGroup = FormGroup;


  handleUpdateCustomer(p: Customer) {

  }

  handleDeleteCustomer(p: Customer) {
    let  conf=confirm("Are you sure ?")
    if(!conf)
      return;
    this.customersService.deleteCustomer(p.id).subscribe(
      {
        next :(resp)=>{
          this.customers=this.customers.pipe(
            map(data=>{
              let  index = data.indexOf(p);
              data.slice(index,1)
              return data;
            })
          )

    },
        error:err=>{
          console.log(err);

        }
      }
    )
  }
}
