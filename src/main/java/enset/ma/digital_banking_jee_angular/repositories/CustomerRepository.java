package enset.ma.digital_banking_jee_angular.repositories;

import enset.ma.digital_banking_jee_angular.dto.CustomerDTO;
import enset.ma.digital_banking_jee_angular.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

  public   List<Customer> findByNomContains(String nom);
}
