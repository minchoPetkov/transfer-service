package mincho.petkov.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import mincho.petkov.dao.AccountDao;
import mincho.petkov.dao.CurrencyRatesDao;
import mincho.petkov.model.ExchangeRate;
import mincho.petkov.model.ExchangeRateId;
import mincho.petkov.model.Transfer;
import mincho.petkov.model.dto.Account;

@Singleton
public class TransferServiceImpl implements TransferService {

    private final AccountDao accountDao;
    private final CurrencyRatesDao currencyRatesDao;

    @Inject
    public TransferServiceImpl(AccountDao accountDao, CurrencyRatesDao currencyRatesDao) {
        this.accountDao = accountDao;
        this.currencyRatesDao = currencyRatesDao;
    }

    @Override
    public void makeTransfer(Transfer transfer) throws IllegalStateException {

        Account accountFrom = transfer.getAccountFrom();
        Account accountTo = transfer.getAccountTo();

        ExchangeRateId exchangeRateId = new ExchangeRateId(accountFrom.getCurrency(), accountTo.getCurrency());
        ExchangeRate exchangeRate = currencyRatesDao.getCurrencyRate(exchangeRateId).orElseThrow(IllegalArgumentException::new);

        accountDao.transfer(transfer, exchangeRate);

    }

}
