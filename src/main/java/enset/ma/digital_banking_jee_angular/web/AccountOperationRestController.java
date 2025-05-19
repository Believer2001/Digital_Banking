package enset.ma.digital_banking_jee_angular.web;


import enset.ma.digital_banking_jee_angular.dto.AccountHitoryDTO;
import enset.ma.digital_banking_jee_angular.dto.AccountOperationDTO;
import enset.ma.digital_banking_jee_angular.dto.BankAccountDTO;
import enset.ma.digital_banking_jee_angular.exceptions.BankAcountNotFoundException;
import enset.ma.digital_banking_jee_angular.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AccountOperationRestController {

    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory( @PathVariable  String  accountId)
    {
        return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHitoryDTO getAccountHistory(@PathVariable  String  accountId,
                                                  @RequestParam(name = "page",defaultValue = "0") int page,
                                                  @RequestParam(name = "size",defaultValue = "5") int size) throws BankAcountNotFoundException
    {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
}
