import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AccountsService} from '../services/accounts.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-accounts',
  imports: [
    RouterLink
  ],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements  OnInit{

  accounts: any;

  constructor(private accountService:AccountsService) {

  }
  ngOnInit(): void {
     this.getAllAccounts()
  }

  getAllAccounts(){
    this.accountService.getAllAccounts().subscribe({
 next : resp =>{
   this.accounts=resp;
 },
    error: err=>
    {
      console.log(err);
    }
    });
  }



}
