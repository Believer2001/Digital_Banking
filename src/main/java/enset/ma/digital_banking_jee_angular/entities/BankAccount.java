package enset.ma.digital_banking_jee_angular.entities;

import enset.ma.digital_banking_jee_angular.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4,discriminatorType = DiscriminatorType.STRING)
public abstract class BankAccount {
    @Id
    private  String id;
    private  double balance ;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
     private AccountStatus status;
     @ManyToOne
     private  Customer customer;
     @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
     private List<AccountOperation> accountOperations;

}
