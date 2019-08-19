package mincho.petkov.controller.mapper;

import mincho.petkov.model.dto.Account;
import mincho.petkov.model.dto.AccountDetail;

public class AccountMapper {

    private AccountMapper() {
    }

    public static AccountDetail mapped(Account account){return new AccountDetail(account.getAccountNumber(), account.getCurrency().name(), account.getAccountBalance());};
}
