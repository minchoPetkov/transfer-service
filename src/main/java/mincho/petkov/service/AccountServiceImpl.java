package mincho.petkov.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import mincho.petkov.dao.AccountDao;
import mincho.petkov.model.dto.Account;

import java.util.List;
import java.util.Optional;

@Singleton
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    @Inject
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> getAllAccounts() {
        return accountDao.getAll();
    }

    public Optional<Account> getByAccountNumber(String accountId) {
        return accountDao.findByAccountNumber(accountId);
    }
}
