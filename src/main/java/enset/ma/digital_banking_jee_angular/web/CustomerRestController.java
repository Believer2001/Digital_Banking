package enset.ma.digital_banking_jee_angular.web;


import enset.ma.digital_banking_jee_angular.dto.CustomerDTO;
import enset.ma.digital_banking_jee_angular.entities.Customer;
import enset.ma.digital_banking_jee_angular.exceptions.CustomerNotFoundException;
import enset.ma.digital_banking_jee_angular.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public List<CustomerDTO> customers()
    {
        return bankAccountService.lisCustomers();
    }

    @GetMapping("/customers/search")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword)
    {
        return bankAccountService.getCustomerByNom(keyword);
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public  CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException

    {
        return bankAccountService.getCustomer((Long) customerId);
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public  CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO)
    {
       return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public  CustomerDTO updateCustomer(@PathVariable Long customerId,  @RequestBody  CustomerDTO customerDTO)
    {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public void  deleteCustomer(@PathVariable Long id)
    {
     bankAccountService.deleteCustomer(id);
    }
}


