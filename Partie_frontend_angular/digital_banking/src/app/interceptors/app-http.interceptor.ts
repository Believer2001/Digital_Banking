import { HttpInterceptorFn } from '@angular/common/http';
import {AuthService} from '../services/auth.service';
import {inject} from '@angular/core';

export const appHttpInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(AuthService); // injection propre
  const token = authService.accessToken;

  if(authService.isAuthenticated){
    const newRequest = req.clone(
      {
        setHeaders:{
          Authorization:`Bearer ${token}`
        }
      }
    )
    console.log(newRequest);

    return next(newRequest);
  }
   else {
     return next(req);
  }
};
