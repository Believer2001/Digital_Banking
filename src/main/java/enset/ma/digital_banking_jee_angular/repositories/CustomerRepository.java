package enset.ma.digital_banking_jee_angular.repositories;

import enset.ma.digital_banking_jee_angular.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
