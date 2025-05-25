package enset.ma.digital_banking_jee_angular.services;

import enset.ma.digital_banking_jee_angular.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {

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
        return  dtoMapper.fromCurrentAccountToCurrentAccountDTO(savedBankAccount);

    }



    @Override
    public SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
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
        return dtoMapper.fromSavingAccountToSavingAccountDTO(savedBankAccount);
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
    public BankAccountDTO getBankAccount(String id) throws BankAcountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
        .orElseThrow(()-> new BankAcountNotFoundException("BankAccount not found"));

        if (bankAccount instanceof SavingAccount)
        {
            SavingAccount savingAccount = (SavingAccount) bankAccount;

            return dtoMapper.fromSavingAccountToSavingAccountDTO(savingAccount);
        }
        else
        {
            CurrentAccount currentAccount =(CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentAccountToCurrentAccountDTO(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BalanceNotSufficientException,BankAcountNotFoundException
    {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAcountNotFoundException("BankAccount not found"));

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


        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAcountNotFoundException("BankAccount not found"));


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
    public List<BankAccountDTO> bankAccountList()
    {
        List<BankAccount> bankAccounts =bankAccountRepository.findAll();
      List<BankAccountDTO> bankAccountDTOS= bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount)
            {
                SavingAccount savingAccount =(SavingAccount) bankAccount;
                return dtoMapper.fromSavingAccountToSavingAccountDTO(savingAccount);
            }

            else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentAccountToCurrentAccountDTO(currentAccount);
            }
        }).collect(Collectors.toList());

        return bankAccountDTOS ;
    }


    @Override
    public CustomerDTO getCustomer(Long customerId)
    {
       Customer customer= customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("customer not found"));
        return dtoMapper.fromCustomerToCustomerDTO(customer);
    }



    @Override
    public List<CustomerDTO> getCustomerByNom(String customerName) {
        List<Customer> customerslists=customerRepository.findByNomContains(customerName);
         return
        customerslists.stream().map(cust->dtoMapper.fromCustomerToCustomerDTO(cust))
                .collect(Collectors.toList());
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


    @Override
    public List<AccountOperationDTO>  accountHistory(String accountId){
        List<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId);

        return accountOperations.stream()
                .map(op->dtoMapper.fromAccountOperationToAccountOperationDTO(op))
                .collect(Collectors.toList());
    }

    @Override
    public AccountHitoryDTO getAccountHistory(String accountId, int page, int size) throws BankAcountNotFoundException {
        BankAccount bankAccount =bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount==null) throw  new BankAcountNotFoundException("account not found");
        Page<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page,size));
        AccountHitoryDTO accountHitoryDTO = new AccountHitoryDTO();
        List<AccountOperationDTO> accountOperationDTOS= accountOperations.getContent().stream().
                map(op->dtoMapper.fromAccountOperationToAccountOperationDTO(op)).collect(Collectors.toList());

       accountHitoryDTO.setAccountOperationDTOS(accountOperationDTOS);
       accountHitoryDTO.setAccountId(bankAccount.getId());
       accountHitoryDTO.setBalance(bankAccount.getBalance());
       accountHitoryDTO.setSize(size);
       accountHitoryDTO.setCurrentPage(page);
       accountHitoryDTO.setTotalpages(accountOperations.getTotalPages());

        return accountHitoryDTO;
    }


}
