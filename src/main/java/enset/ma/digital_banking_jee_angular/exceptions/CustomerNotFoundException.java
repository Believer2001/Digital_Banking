package enset.ma.digital_banking_jee_angular.exceptions;

public class CustomerNotFoundException extends RuntimeException
{
    public CustomerNotFoundException(String message)
    {
        super(message);
    }
}
