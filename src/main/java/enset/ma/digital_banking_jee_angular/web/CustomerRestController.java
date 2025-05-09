package enset.ma.digital_banking_jee_angular.web;


import enset.ma.digital_banking_jee_angular.dto.CustomerDTO;
import enset.ma.digital_banking_jee_angular.entities.Customer;
import enset.ma.digital_banking_jee_angular.exceptions.CustomerNotFoundException;
import enset.ma.digital_banking_jee_angular.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers()
    {
        return bankAccountService.lisCustomers();
    }

    @GetMapping("/customers/{id}")
    public  CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException

    {
        return bankAccountService.getCustomer((Long) customerId);
    }

    @PostMapping("/customers")
    public  CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO)
    {
       return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerId}")
    public  CustomerDTO updateCustomer(@PathVariable Long customerId,  @RequestBody  CustomerDTO customerDTO)
    {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void  deleteCustomer(@PathVariable Long id)
    {
     bankAccountService.deleteCustomer(id);
    }
}


