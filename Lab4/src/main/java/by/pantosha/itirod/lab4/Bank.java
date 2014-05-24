package by.pantosha.itirod.lab4;

import by.pantosha.itirod.lab4.enities.Account;
import by.pantosha.itirod.lab4.exceptions.InsufficientFundsException;
import by.pantosha.itirod.lab4.repository.AccountDaemon;
import by.pantosha.itirod.lab4.repository.CheckingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Bank {
    private final static Logger LOGGER = LogManager.getLogger(Bank.class);

    private final Random random;

    //private final UUID[] accountsId;
    private final List<UUID> accountsId;
    private final AccountDaemon daemon;
    private final CheckingRepository<Account> repository;

    private int getNumberAccount;

    private Bank(Builder builder) {
        random = new Random();
        accountsId = new ArrayList<>(builder.accounts.keySet());
        Collections.shuffle(accountsId);
        repository = new CheckingRepository<>(builder.accounts);
        daemon = new AccountDaemon(repository, 500);
        daemon.start();
    }

    public void transfer(UUID from, UUID to, int amount) throws InsufficientFundsException {
        try {
            LOGGER.trace("Transfer start. {} => {}: ${}", from, to, amount);
            repository.beginTransaction(from, to);

            Account toAccount = repository.read(to);
            Account fromAccount = repository.read(from);

            fromAccount = fromAccount.withdraw(amount);
            if (fromAccount == null) {
                LOGGER.warn("Transfer failure. {} => {}: ${}", from, to, amount);
                throw new InsufficientFundsException();
            } else {
                toAccount = toAccount.deposit(amount);
                repository.update(toAccount, fromAccount);
                LOGGER.info("Transfer success. {} => {}: ${}", fromAccount, toAccount, amount);
            }
        } finally {
            LOGGER.trace("Transfer end. {} => {}: ${}", from, to, amount);
            repository.endTransaction(from, to);
        }
    }

    public List<Account> getAccountsAndBeginTransaction(UUID... ids) {
        repository.beginTransaction(ids);

        List<Account> accounts = new ArrayList<>();
        for (UUID id : ids) {
            accounts.add(repository.read(id));
        }
        return accounts;
    }

    public void transferForAccountsInTransaction(UUID from, UUID to, int amount) {
        Account fromAccount = repository.read(from);
        Account toAccount = repository.read(to);

        fromAccount = fromAccount.withdraw(amount);
        if (fromAccount == null) {
            LOGGER.warn("Transfer failure. {} => {}: ${}", from, to, amount);
        } else {
            toAccount = toAccount.deposit(amount);
            repository.update(fromAccount, toAccount);
            LOGGER.info("Transfer success. {} => {}: ${}", fromAccount, toAccount, amount);
        }
    }

    public void endTransaction(UUID... ids) {
        repository.endTransaction(ids);
    }

    synchronized UUID getRandomAccountId() {
        if (getNumberAccount >= 1000) {
            Collections.shuffle(accountsId);
            getNumberAccount = 0;
        }
        getNumberAccount++;
        return accountsId.get(random.nextInt(accountsId.size()));
    }

    public static class Builder implements IBuilder<Bank> {
        private Map<UUID, Account> accounts;
        private int initialBalance;
        private int accountsNumber;

        public Bank build() {
            generateAccounts();
            return new Bank(this);
        }

        public Builder setInitialBalance(int balance) {
            initialBalance = balance;
            return this;
        }

        public Builder setAccountsNumber(int accounts) {
            accountsNumber = accounts;
            return this;
        }

        private Builder generateAccounts() {
            accounts = new HashMap<>(accountsNumber);
            for (int i = 0; i < accountsNumber; i++) {
                Account account = new Account(initialBalance / accountsNumber);
                accounts.put(account.getId(), account);
            }
            return this;
        }
    }
}
