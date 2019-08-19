package mincho.petkov.dao;

import mincho.petkov.model.ExchangeRate;
import mincho.petkov.model.Transfer;
import mincho.petkov.model.dto.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDao {

    /**
     * Get all accounts from datasource
     * @return list of accounts
     */
    List<Account> getAll();

    /**
     * get account by Id from datasource
     * @param accountNumber
     * @return Account if exist
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    Account save(Account account);

    void transfer(Transfer transfer, ExchangeRate exchangeRate);

}
