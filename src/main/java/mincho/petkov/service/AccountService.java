package mincho.petkov.service;

import mincho.petkov.model.dto.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    /**
     * Get all accounts
     * @return list of all accounts
     */
    List<Account> getAllAccounts();

    /**
     * Get account by Id
     * @param accountId
     * @return optional of Account
     */
    Optional<Account> getByAccountNumber(String accountId);
}
