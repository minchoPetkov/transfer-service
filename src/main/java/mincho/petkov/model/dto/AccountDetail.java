package mincho.petkov.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountDetail {

    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("accountBalance")
    private BigDecimal accountBalance;

    public AccountDetail() {
    }

    public AccountDetail(String accountNumber, String currency, BigDecimal accountBalance) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
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
        AccountDetail account = (AccountDetail) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(currency, account.currency) &&
                Objects.equals(accountBalance, account.accountBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, currency, accountBalance);
    }
}
