package by.pantosha.itirod.lab4.exceptions;

import by.pantosha.itirod.lab4.enities.Account;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(Account account) {
        this("Insufficient funds available in account " + account.getId());
    }

    public InsufficientFundsException() {
        super("Insufficient funds available in account");
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(Throwable cause) {
        super(cause);
    }
}
