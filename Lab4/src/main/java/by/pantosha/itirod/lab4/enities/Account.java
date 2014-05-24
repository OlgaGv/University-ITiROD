package by.pantosha.itirod.lab4.enities;

import java.util.UUID;

public final class Account implements Entity {
    private final UUID id;
    private final int balance;

    public Account(int balance) {
        this(balance, UUID.randomUUID());
    }

    public Account(int balance, UUID id) {
        if (balance < 0)
            throw new IllegalArgumentException("balance less than 0.");

        this.id = id;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }

    public Account deposit(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException();

        return new Account(balance + amount, getId());
    }

    public Account withdraw(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException();
        boolean operationSuccess = amount <= balance;
        if (operationSuccess)
            return new Account(balance - amount, getId());
        else
            return null;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Balance: $%d", getId(), getBalance());
    }
}
