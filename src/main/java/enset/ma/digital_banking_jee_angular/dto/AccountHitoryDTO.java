package enset.ma.digital_banking_jee_angular.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountHitoryDTO {

    private  String accountId;
    private  double balance;
    private  int currentPage;
    private  int totalpages;
    private  int size;
    private List<AccountOperationDTO> accountOperationDTOS;

}
