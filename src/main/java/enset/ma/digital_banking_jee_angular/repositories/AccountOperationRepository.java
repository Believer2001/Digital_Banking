package enset.ma.digital_banking_jee_angular.repositories;

import enset.ma.digital_banking_jee_angular.entities.AccountOperation;
import enset.ma.digital_banking_jee_angular.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
