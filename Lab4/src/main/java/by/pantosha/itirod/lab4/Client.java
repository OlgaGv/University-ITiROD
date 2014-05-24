package by.pantosha.itirod.lab4;

import by.pantosha.itirod.lab4.exceptions.InsufficientFundsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.UUID;

public class Client extends Thread {
    private final Logger LOGGER = LogManager.getLogger(Client.class);
    private final Bank bank;
    private final Random random = new Random();

    public Client(Bank bank) {
        this.bank = bank;
    }

    public Client(Bank bank, String name) {
        super(name);
        this.bank = bank;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(random.nextInt(100));
                transfer();
            } catch (Exception e) {
                LOGGER.error("Fail to provide transfer. Exception " + e.getMessage());
            }
        }
    }

    public void transfer() {
        UUID from = bank.getRandomAccountId();
        UUID to = bank.getRandomAccountId();
        if (from.compareTo(to) != 0) {
            int amount = random.nextInt(1000);
            transfer(from, to, amount);
        }
    }

    public void transfer(UUID from, UUID to, int amount) {
        try {
            bank.transfer(from, to, amount);
        } catch (InsufficientFundsException ex) {
            LOGGER.info("Insufficient Funds.");
        }
    }
}
