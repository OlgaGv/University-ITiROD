package by.pantosha.itirod.lab4;

import by.pantosha.itirod.lab4.enities.Account;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BlockingClient extends Thread {
    private final Logger LOGGER = LogManager.getLogger(BlockingClient.class);
    private final Bank bank;
    private final Random random = new Random();

    public BlockingClient(Bank bank) {
        this.bank = bank;
    }

    public BlockingClient(Bank bank, String name) {
        super(name);
        this.bank = bank;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                sleep(random.nextInt(200));
                transfer();
            }
        } catch (Exception e) {
            LOGGER.error("Fail to provide transfer.");
            e.printStackTrace(LOGGER.getStream(Level.ERROR));
            e.printStackTrace();
        }
    }

    public void transfer() {
        UUID accountId1 = bank.getRandomAccountId();
        UUID accountId2 = bank.getRandomAccountId();

        if (accountId1.compareTo(accountId2) == 0) {
            return;
        }
        List<Account> accounts = bank.getAccountsAndBeginTransaction(accountId1, accountId2);

        Account fromAccount = accounts.get(0);
        Account toAccount = accounts.get(1);

        int balance = fromAccount.getBalance();
        if (balance > 0) {
            int amount = random.nextInt(fromAccount.getBalance() / 10);
                bank.transferForAccountsInTransaction(fromAccount.getId(), toAccount.getId(), amount);
        }
        bank.endTransaction(fromAccount.getId(), toAccount.getId());
    }
}