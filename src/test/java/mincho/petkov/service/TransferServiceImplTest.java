package mincho.petkov.service;


import mincho.petkov.dao.AccountDao;
import mincho.petkov.dao.CurrencyRatesDao;
import mincho.petkov.dao.StubAccountDaoImpl;
import mincho.petkov.dao.StubCurrencyRatesDaoImpl;
import mincho.petkov.model.Transfer;
import mincho.petkov.model.dto.Account;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static mincho.petkov.model.Currency.EUR;
import static mincho.petkov.model.Currency.GBP;
import static org.junit.Assert.assertEquals;

public class TransferServiceImplTest {


    private AccountDao accountDao;
    private TransferService transferService;

    @Before
    public void setUp() {
        accountDao = new StubAccountDaoImpl();
        CurrencyRatesDao currencyRatesDao = new StubCurrencyRatesDaoImpl();
        transferService = new TransferServiceImpl(accountDao, currencyRatesDao);
    }


    @Test
    public void testSimpleTransfer() {
        Account account1 = new Account("euroAccount1", EUR, BigDecimal.valueOf(15000));
        Account account2 = new Account("euroAccount2", EUR, BigDecimal.valueOf(15000));
        accountDao.save(account1);
        accountDao.save(account2);


        transferService.makeTransfer(new Transfer(account1, account2, new BigDecimal("125.5")));

        assertEquals(account1.getAccountBalance(), BigDecimal.valueOf(14874.50).setScale(2, RoundingMode.CEILING));
        assertEquals(account2.getAccountBalance(), BigDecimal.valueOf(15125.50).setScale(2, RoundingMode.CEILING));

    }

    @Test
    public void testConversionTransfer() {
        Account account1 = new Account("gbpAccount1", GBP, BigDecimal.valueOf(15000));
        Account account2 = new Account("euroAccount2", EUR, BigDecimal.valueOf(15000));

        accountDao.save(account1);
        accountDao.save(account2);

        transferService.makeTransfer(new Transfer(account1, account2, new BigDecimal("125.5")));

        assertEquals(account1.getAccountBalance(), BigDecimal.valueOf(14874.50).setScale(2, RoundingMode.CEILING));
        assertEquals(account2.getAccountBalance(), BigDecimal.valueOf(15138.05));

    }

    @Test(expected = IllegalStateException.class)
    public void testNotEnoughFunds() {
        Account account1 = new Account("gbpAccount1", GBP, BigDecimal.valueOf(1));
        Account account2 = new Account("euroAccount2", EUR, BigDecimal.valueOf(15000));

        accountDao.save(account1);
        accountDao.save(account2);

        transferService.makeTransfer(new Transfer(account1, account2, new BigDecimal("125.5")));

    }

}