package mincho.petkov.model;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

@Immutable
public final class ExchangeRateId {
    private final Currency from;
    private final Currency to;

    public ExchangeRateId(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateId that = (ExchangeRateId) o;
        return from == that.from &&
                to == that.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }


    @Override
    public String toString() {
        return "ExchangeRateId{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
