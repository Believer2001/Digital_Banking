package enset.ma.digital_banking_jee_angular.dto;


import enset.ma.digital_banking_jee_angular.enums.AccountStatus;

import lombok.Data;


import java.util.Date;



@Data

public  class SavingAccountDTO  extends BankAccountDTO{
    private  String id;
    private  double balance ;
    private Date createdAt;
     private AccountStatus status;
     private CustomerDTO customerDTO;
     private  double interestRate;

}
