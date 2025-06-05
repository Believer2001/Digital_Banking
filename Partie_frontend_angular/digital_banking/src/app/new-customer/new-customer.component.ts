import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {Customer} from '../model/customer.model';
import {CustomersService} from '../services/customers.service';
import {Router} from '@angular/router';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-new-customer',
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent {

  newCustomerFormGroup!: FormGroup

  constructor(private  fb:FormBuilder,private  customerService: CustomersService,
              private router:Router) {

  }

  ngOnInit():void{
    this.newCustomerFormGroup=this.fb.group( {
      nom : this.fb.control(null,[Validators.required,Validators.minLength(2)]),
        email :this.fb.control(null,[Validators.email,Validators.required]),


    })
  }


  handleSaveCustomer(): void {

    let  customer : Customer =this.newCustomerFormGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
      next :data=>{
        alert("Customer has been successfully  saved");
       /// this.newCustomerFormGroup.reset();

        this.router.navigateByUrl("/customers");
      },error : err => { console.log(err);
      }
      });
  }

  protected readonly Validators = Validators;
}
