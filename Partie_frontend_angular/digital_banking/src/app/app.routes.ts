import { Routes } from '@angular/router';
import {CustomersComponent} from './customers/customers.component';
import {AccountsComponent} from './accounts/accounts.component';
import {NewCustomerComponent} from './new-customer/new-customer.component';
import {LoginComponent} from './login/login.component';

export const routes: Routes = [
  {path:"login",component:LoginComponent},
  {path:"",redirectTo: "/login", pathMatch:'full'},
  {path:"customers",component: CustomersComponent},
  {path:"accounts",component:AccountsComponent},
  {path:"new_customer",component:NewCustomerComponent}

];
