import { Routes } from '@angular/router';
import {CustomersComponent} from './customers/customers.component';
import {AccountsComponent} from './accounts/accounts.component';
import {NewCustomerComponent} from './new-customer/new-customer.component';
import {LoginComponent} from './login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {DashbordAdminComponent} from './dashbord-admin/dashbord-admin.component';
import {AdminTemplateComponent} from './admin-template/admin-template.component';
import {authenticationGuard} from './guards/authentication.guard';
import {NotAutorizedComponent} from './not-autorized/not-autorized.component';
import {authorizationGuard} from './guards/authorization.guard';

export const routes: Routes = [
  {path:"login",component:LoginComponent},
  {path:"",redirectTo: "/login", pathMatch:'full'},

  {path: "admin", component:AdminTemplateComponent, canActivate:[authenticationGuard],
    children : [

      {path:"customers",component: CustomersComponent},
      {path:"accounts",component:AccountsComponent},
      {path:"new_customer",component:NewCustomerComponent,canActivate :[authorizationGuard],data:{role:"ADMIN"}},
      {path: "dashboard",component:DashboardComponent},
      {path: "notAuthorized",component:NotAutorizedComponent},

      {path: "dashboard_admin",component:DashbordAdminComponent}


    ]},


];
