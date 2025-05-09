package enset.ma.digital_banking_jee_angular.services;

import enset.ma.digital_banking_jee_angular.entities.*;
import enset.ma.digital_banking_jee_angular.enums.OperationType;
import enset.ma.digital_banking_jee_angular.exceptions.BalanceNotSufficientException;
import enset.ma.digital_banking_jee_angular.exceptions.BankAcountNotFoundException;
import enset.ma.digital_banking_jee_angular.exceptions.CustomerNotFoundException;
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


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements  BankAccountService{


    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;


    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("saving a new customer");
        Customer savedCustomer= customerRepository.save(customer);
        return savedCustomer;
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
    public List<Customer> lisCustomers() {
        return customerRepository.findAll();
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
}
