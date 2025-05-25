import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Observable} from 'rxjs';
import {Customer} from '../model/customer.model';
import {CustomersService} from '../services/customers.service';

@Component({
  selector: 'app-new-customer',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent {

  newCustomerFormGroup!: FormGroup

  constructor(private  fb:FormBuilder,private  customerService: CustomersService) {

  }

  ngOnInit():void{
    this.newCustomerFormGroup=this.fb.group( {
      nom : this.fb.control(null),
        email :this.fb.control(null),


    })
  }


  handleSaveCustomer(): void {

    let  customer : Customer =this.newCustomerFormGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
      next :data=>{
        alert("Customer has been successfully  saved");
      },error : err => { console.log(err);
      }
      });
  }
}
