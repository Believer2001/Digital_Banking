package enset.ma.digital_banking_jee_angular.repositories;

import enset.ma.digital_banking_jee_angular.entities.BankAccount;
import enset.ma.digital_banking_jee_angular.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String>
{
}