package by.pantosha.itirod.lab4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lab4 {
    private final static Logger LOGGER = LogManager.getLogger(Lab4.class);

    private static final int INITIAL_BALANCE = 1000000;
    private static final int ACCOUNTS_NUMBER = 40;
    private static final int CLIENTS_NUMBER = 50;

    public static void main(String... args) {
        Bank.Builder bankBuilder = new Bank.Builder();
        Bank bank = bankBuilder.setAccountsNumber(ACCOUNTS_NUMBER)
                .setInitialBalance(INITIAL_BALANCE)
                .build();

        for (int i = 0; i < CLIENTS_NUMBER; i++) {
            BlockingClient client = new BlockingClient(bank, Integer.toString(i));
            client.start();
        }
    }
}