package network_account;

/**
 * Produce an exception for problems with login
 */
public class AccountException extends RuntimeException {
    /**
     * Creates an exception with error message "message"
     * @param message is the message of the error
     */
    public AccountException(String message){
        super(message);
    }
}
