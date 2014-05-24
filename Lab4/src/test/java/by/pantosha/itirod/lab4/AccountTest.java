package by.pantosha.itirod.lab4;

import by.pantosha.itirod.lab4.enities.Account;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

public class AccountTest {

    private final Random random = new Random();

    @DataProvider
    public Object[][] validAccounts() {
        return new Object[][]{
                {new Account(0)},
                {new Account(1)},
                {new Account(10)},
                {new Account(Math.abs(random.nextInt()))}
        };
    }

    @Test(dataProvider = "validAccounts")
    public void testWithdrawException(Account account) {
        Account accountAfterOperation = account.withdraw(account.getBalance() + 1);
        AssertJUnit.assertNull(accountAfterOperation);
    }

    @Test(dataProvider = "validAccounts")
    public void testWithdraw(Account account) {
        int balance = account.getBalance();
        int amount = (balance > 0) ? random.nextInt(balance) : 0;
        Account accountAfterOperation = account.withdraw(amount);
        AssertJUnit.assertNotNull(accountAfterOperation);
        AssertJUnit.assertEquals(account.getBalance() - amount, accountAfterOperation.getBalance());
    }
}
