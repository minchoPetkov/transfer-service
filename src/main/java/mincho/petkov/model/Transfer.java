package mincho.petkov.model;

import mincho.petkov.model.dto.Account;

import javax.annotation.concurrent.Immutable;
import java.math.BigDecimal;
import java.util.Objects;

@Immutable
public final class Transfer {

    private final Account accountFrom;
    private final Account accountTo;
    private final BigDecimal amount;

    public Transfer(Account accountFrom, Account accountTo, BigDecimal amount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer that = (Transfer) o;
        return Objects.equals(accountFrom, that.accountFrom) &&
                Objects.equals(accountTo, that.accountTo) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountFrom, accountTo, amount);
    }

    @Override
    public String toString() {
        return "TransferRequest{" +
                "accountFrom='" + accountFrom + '\'' +
                ", accountTo='" + accountTo + '\'' +
                ", amount=" + amount +
                '}';
    }
}
