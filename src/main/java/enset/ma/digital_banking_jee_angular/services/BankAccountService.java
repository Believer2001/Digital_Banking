package enset.ma.digital_banking_jee_angular.services;

import enset.ma.digital_banking_jee_angular.entities.BankAccount;
import enset.ma.digital_banking_jee_angular.entities.CurrentAccount;
import enset.ma.digital_banking_jee_angular.entities.Customer;
import enset.ma.digital_banking_jee_angular.entities.SavingAccount;
import enset.ma.digital_banking_jee_angular.exceptions.BalanceNotSufficientException;
import enset.ma.digital_banking_jee_angular.exceptions.BankAcountNotFoundException;
import enset.ma.digital_banking_jee_angular.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<Customer> lisCustomers();
    BankAccount getBankAccount(String id) throws BankAcountNotFoundException;
    void debit(String accountId,double amount, String description) throws BankAcountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount, String description) throws  BankAcountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination,double amount) throws BankAcountNotFoundException,BalanceNotSufficientException;

    List<BankAccount> bankAccountList();
}
