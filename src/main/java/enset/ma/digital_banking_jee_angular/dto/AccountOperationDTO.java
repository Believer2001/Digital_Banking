package enset.ma.digital_banking_jee_angular.dto;

import enset.ma.digital_banking_jee_angular.entities.BankAccount;
import enset.ma.digital_banking_jee_angular.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data

public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private  double amount;
    private OperationType operationType;
    private String description;
}
