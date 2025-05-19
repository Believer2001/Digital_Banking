
import {Component, OnInit} from '@angular/core';
import {CustomersService} from '../services/customers.service';
import {JsonPipe, NgIf} from '@angular/common';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-customers',
  imports: [
    NgIf,
    ReactiveFormsModule,
    JsonPipe


  ],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements  OnInit{

  customers: any;
  searchFormGroup!: FormGroup;
  constructor(private customersService:CustomersService,private  fb :FormBuilder) {
  }
  ngOnInit() {

    this.searchFormGroup =this.fb.group({keyword :this.fb.control("")});
    this.getAllCustomers()
  }
 getAllCustomers()
 {
   this.customersService.getAllCustomers().subscribe({
     next : resp =>{
       this.customers =resp;
   },
     error : err => {
     console.log(err);}
     }
   );


 }

  handleSearchCustomer() {
 let kw = this.searchFormGroup?.value.keyword
  }
}
