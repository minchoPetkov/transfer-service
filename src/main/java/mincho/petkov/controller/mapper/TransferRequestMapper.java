package mincho.petkov.controller.mapper;

import mincho.petkov.model.Transfer;
import mincho.petkov.model.dto.Account;

import java.math.BigDecimal;
import java.util.Optional;

public class TransferRequestMapper {


    private TransferRequestMapper() {
    }

    public static Transfer mapped(BigDecimal transferAmount, Optional<Account> from, Optional<Account> to) {
        Account fromAccount = from.orElseThrow(IllegalArgumentException::new);
        Account toAccount = to.orElseThrow(IllegalArgumentException::new);
        return new Transfer(fromAccount, toAccount, transferAmount);
    }


}
