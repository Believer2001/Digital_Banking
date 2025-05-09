package enset.ma.digital_banking_jee_angular.mappers;


import enset.ma.digital_banking_jee_angular.dto.CustomerDTO;
import enset.ma.digital_banking_jee_angular.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMappersImpl {

    public CustomerDTO fromCustomerToCustomerDTO(Customer customer)

    {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer ,customerDTO);
        return customerDTO;
    }

    public Customer fromCustomerDTOToCustomer(CustomerDTO customerDTO)
    {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return  customer;
    }
}
