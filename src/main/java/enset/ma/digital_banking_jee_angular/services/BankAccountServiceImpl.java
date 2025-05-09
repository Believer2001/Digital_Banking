package enset.ma.digital_banking_jee_angular.services;

import enset.ma.digital_banking_jee_angular.dto.CustomerDTO;
import enset.ma.digital_banking_jee_angular.entities.*;
import enset.ma.digital_banking_jee_angular.enums.OperationType;
import enset.ma.digital_banking_jee_angular.exceptions.BalanceNotSufficientException;
import enset.ma.digital_banking_jee_angular.exceptions.BankAcountNotFoundException;
import enset.ma.digital_banking_jee_angular.exceptions.CustomerNotFoundException;
import enset.ma.digital_banking_jee_angular.mappers.BankAccountMappersImpl;
import enset.ma.digital_banking_jee_angular.repositories.AccountOperationRepository;
import enset.ma.digital_banking_jee_angular.repositories.BankAccountRepository;
import enset.ma.digital_banking_jee_angular.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements  BankAccountService{


    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;

    private BankAccountMappersImpl dtoMapper;


    @Override

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("saving a new customer");
        Customer customer =dtoMapper.fromCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return dtoMapper.fromCustomerToCustomerDTO(savedCustomer);
    }



    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {

        log.info("saving a new current account");
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer == null)
        {
            throw new CustomerNotFoundException("customer not found");
        }
        CurrentAccount currentAccount =new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);

        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return  savedBankAccount;

    }



    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        log.info("saving a new savning account");
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer == null)
        {
            throw new CustomerNotFoundException("customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);

        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return savedBankAccount;
    }




    @Override
    public List<CustomerDTO> lisCustomers() {
        List<Customer> customers= customerRepository.findAll();

     List<CustomerDTO> customerDTOS= customers.stream()
             .map(customer -> dtoMapper.fromCustomerToCustomerDTO(customer))
                .collect(Collectors.toList());

     /*   List<CustomerDTO>  customerDTOS = new ArrayList<>();


        for (Customer customer : customers )

        {
            CustomerDTO customerDTO = dtoMapper.fromCustomerToCustomerDTO(customer);
        }*/

        return  customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String id) throws BankAcountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
        .orElseThrow(()-> new BankAcountNotFoundException("BankAccount not found"));
        return  bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BalanceNotSufficientException,BankAcountNotFoundException
    {
      BankAccount bankAccount =getBankAccount(accountId);
      if(bankAccount.getBalance()<amount)
      {
          throw new BalanceNotSufficientException("Balance not suffisant");
      }
       AccountOperation accountOperation = new AccountOperation();
       accountOperation.setOperationType(OperationType.DEBIT);
       accountOperation.setAmount(amount);
       accountOperation.setDescription(description);
       accountOperation.setOperationDate(new Date());
       accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

       bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws  BankAcountNotFoundException
    {


        BankAccount bankAccount =getBankAccount(accountId);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()+amount);
         bankAccountRepository.save(bankAccount);


    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAcountNotFoundException,BalanceNotSufficientException{

        debit(accountIdDestination,amount,"transfert to  "+accountIdDestination);
        credit(accountIdDestination,amount,"transfert from "+accountIdSource);


    }

    @Override
    public List<BankAccount> bankAccountList()
    {
        return bankAccountRepository.findAll();
    }


    @Override
    public CustomerDTO getCustomer(Long customerId)
    {
       Customer customer= customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("customer not found"));
        return dtoMapper.fromCustomerToCustomerDTO(customer);
    }


@Override
public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("saving a new customer");
        Customer customer =dtoMapper.fromCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return dtoMapper.fromCustomerToCustomerDTO(savedCustomer);
    }

    @Override
    public void  deleteCustomer(Long customerID)
    {
        customerRepository.deleteById(customerID);
    }

}
