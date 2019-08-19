package mincho.petkov.dao;

import com.google.inject.Singleton;
import mincho.petkov.model.ExchangeRate;
import mincho.petkov.model.Transfer;
import mincho.petkov.model.dto.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Singleton
public class StubAccountDaoImpl implements AccountDao {

    private final Map<String, Account> accountStorage = new ConcurrentHashMap<>();

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(accountStorage.values());
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return Optional.ofNullable(accountStorage.get(accountNumber));
    }

    @Override
    public Account save(Account account) {
        return accountStorage.put(account.getAccountNumber(), account);
    }

    @Override
    // In the future this will be handled with the transactional manager for the DB
    public void transfer(Transfer transfer, ExchangeRate exchangeRate) {
        synchronized (accountStorage) {
            Account from = accountStorage.get(transfer.getAccountFrom().getAccountNumber());
            Account to = accountStorage.get(transfer.getAccountTo().getAccountNumber());
            BigDecimal transferAmountFrom = transfer.getAmount();
            BigDecimal transferAmountTo = transferAmountFrom.multiply(exchangeRate.getExchangeRate());
            if (from.getAccountBalance().compareTo(transferAmountFrom) < 0) {
                throw new IllegalStateException("Account " + from + " has insufficient funds (requested: " + transferAmountFrom + "; actual: " + from.getAccountBalance());
            }
            from.setAccountBalance(from.getAccountBalance().subtract(transferAmountFrom));
            to.setAccountBalance(to.getAccountBalance().add(transferAmountTo));

            save(from);
            save(to);
        }
    }

}
