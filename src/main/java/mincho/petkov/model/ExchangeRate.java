package mincho.petkov.model;

import javax.annotation.concurrent.Immutable;
import java.math.BigDecimal;
import java.util.Objects;

@Immutable
public final class ExchangeRate {
    private final ExchangeRateId exchangeRateId;
    private final BigDecimal exchangeRate;

    public ExchangeRate(ExchangeRateId exchangeRateId, BigDecimal exchangeRate) {
        this.exchangeRateId = exchangeRateId;
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public ExchangeRateId getExchangeRateId() {
        return exchangeRateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(exchangeRateId, that.exchangeRateId) &&
                Objects.equals(exchangeRate, that.exchangeRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeRateId, exchangeRate);
    }


    @Override
    public String toString() {
        return "ExchangeRate{" +
                "exchangeRateId=" + exchangeRateId +
                ", exchangeRate=" + exchangeRate +
                '}';
    }
}
