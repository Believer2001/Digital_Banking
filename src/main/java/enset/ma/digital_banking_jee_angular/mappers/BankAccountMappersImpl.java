package enset.ma.digital_banking_jee_angular.mappers;


import enset.ma.digital_banking_jee_angular.dto.*;
import enset.ma.digital_banking_jee_angular.entities.*;
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



    public SavingAccountDTO fromSavingAccountToSavingAccountDTO(SavingAccount savingAccount)
    {
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingAccountDTO);
        savingAccountDTO.setCustomerDTO(fromCustomerToCustomerDTO(savingAccount.getCustomer()));
        return  savingAccountDTO;
    }

    public  SavingAccount fromSavingAccountDTOToSavingAccount(SavingAccountDTO savingAccountDTO)
    {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTOToCustomer(savingAccountDTO.getCustomerDTO()));
        return  savingAccount;
    }


    //  mapping pour le CurrentAccount

    public CurrentAccountDTO fromCurrentAccountToCurrentAccountDTO(CurrentAccount currentAccount)
    {
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentAccountDTO);
        currentAccountDTO.setCustomerDTO(fromCustomerToCustomerDTO(currentAccount.getCustomer()));
        return  currentAccountDTO;
    }

    public  CurrentAccount fromCurrentAccountDTOToCurrentAccount(CurrentAccountDTO currentAccountDTO)
    {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTOToCustomer(currentAccountDTO.getCustomerDTO()));
        return  currentAccount;
    }



    public AccountOperationDTO fromAccountOperationToAccountOperationDTO(AccountOperation accountOperation)
    {
        AccountOperationDTO accountOperationDTO =new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }

    public  AccountOperation fromAccountOperationDTOToAccountOperation(AccountOperationDTO accountOperationDTO)
    {
        AccountOperation accountOperation =new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO,accountOperation);
        return  accountOperation;
    }
}
