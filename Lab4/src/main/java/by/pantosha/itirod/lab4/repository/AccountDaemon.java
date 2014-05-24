package by.pantosha.itirod.lab4.repository;

import by.pantosha.itirod.lab4.enities.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public final class AccountDaemon extends Thread {
    private final static Logger LOGGER = LogManager.getLogger(AccountDaemon.class);

    private final long millis;
    private final CheckingRepository<Account> repository;

    private int sum;
    private int previousSum;

    public AccountDaemon(CheckingRepository<Account> repository, long millis) {
        sum = 0;
        previousSum = -1;
        this.millis = millis;
        this.repository = repository;
    }

    public void startCheck() {
        if (checking())
            LOGGER.info("Checking end success.");
        else
            LOGGER.fatal(String.format("Warning! Funds stolen. Before: %d, After: %d", previousSum, sum));
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            LOGGER.debug("Checking start.");
            startCheck();
            try {
                sleep(millis);
            } catch (InterruptedException ex) {
                LOGGER.warn(ex.getMessage());
            }
        }
    }

    private boolean checking() {
        sum = 0;

        repository.forEach(new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                sum += account.getBalance();
                LOGGER.trace("Check account: {}", account);
            }
        });

        if (sum != previousSum)
            if (previousSum == -1) {
                previousSum = sum;
            } else {
                return false;
            }
        return true;
    }
}
