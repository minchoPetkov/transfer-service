package mincho.petkov.model.dto;

import mincho.petkov.model.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Account {

    private final String accountNumber;

    private final Currency currency;
    private BigDecimal accountBalance;


    public Account(String accountNumber, Currency currency, BigDecimal accountBalance) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.accountBalance = accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", currency='" + currency + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(currency, account.currency) &&
                Objects.equals(accountBalance, account.accountBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, currency, accountBalance);
    }
}
