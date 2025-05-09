package enset.ma.digital_banking_jee_angular;

import enset.ma.digital_banking_jee_angular.dto.CustomerDTO;
import enset.ma.digital_banking_jee_angular.entities.*;
import enset.ma.digital_banking_jee_angular.enums.AccountStatus;
import enset.ma.digital_banking_jee_angular.enums.OperationType;
import enset.ma.digital_banking_jee_angular.exceptions.BalanceNotSufficientException;
import enset.ma.digital_banking_jee_angular.exceptions.BankAcountNotFoundException;
import enset.ma.digital_banking_jee_angular.exceptions.CustomerNotFoundException;
import enset.ma.digital_banking_jee_angular.repositories.AccountOperationRepository;
import enset.ma.digital_banking_jee_angular.repositories.BankAccountRepository;
import enset.ma.digital_banking_jee_angular.repositories.CustomerRepository;
import enset.ma.digital_banking_jee_angular.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingJeeAngularApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(DigitalBankingJeeAngularApplication.class, args);
    }

 @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService)
    {
        return args -> {
            Stream.of("Hassan", "Imane","Mohammed").forEach(name ->{

                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setNom(name);
                customerDTO.setEmail(name+"@gmail.com");

                bankAccountService.saveCustomer(customerDTO);
            });
            bankAccountService.lisCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 120000, 5.5, customer.getId());
                    List<BankAccount> bankAccount = bankAccountService.bankAccountList();
                    for (BankAccount bankAccount1 : bankAccount) {
                        for (int i = 0; i < 10; i++) {
                            bankAccountService.credit(bankAccount1.getId(), 10000 + Math.random() * 12000, "Credit");
                            bankAccountService.debit(bankAccount1.getId(), 1000 + Math.random() * 9000, "Debit");
                        }
                    }

                } catch (BalanceNotSufficientException e) {
                    throw new RuntimeException(e);
                } catch (BankAcountNotFoundException e) {
                    e.printStackTrace();
                } catch (CustomerNotFoundException e) {
                    throw new RuntimeException(e);
                }


            } );
        };
    }










//@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            AccountOperationRepository accountOperationRepository,
                            BankAccountRepository bankAccountRepository)
    {
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                Customer customer =new Customer();
                customer.setNom(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                 bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);
               bankAccountRepository.save(savingAccount);
            });


            bankAccountRepository.findAll().forEach(account->{
                for(int i =0;i<10;i++)
                {
                    AccountOperation accountOperation =new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setOperationType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(account);
                    accountOperationRepository.save(accountOperation);
                }
            });


        };

    }
}
