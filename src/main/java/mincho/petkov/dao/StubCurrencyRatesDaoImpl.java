package mincho.petkov.dao;

import com.google.inject.Singleton;
import mincho.petkov.model.Currency;
import mincho.petkov.model.ExchangeRate;
import mincho.petkov.model.ExchangeRateId;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static mincho.petkov.model.Currency.EUR;
import static mincho.petkov.model.Currency.GBP;
import static mincho.petkov.model.Currency.USD;

/**
 * This is a stub of the exchange rate. This is only for testing purposes. It should read from a db in the future.
 */
@Singleton
public class StubCurrencyRatesDaoImpl implements CurrencyRatesDao {


    private final Map<ExchangeRateId, ExchangeRate> currencyRates = new ConcurrentHashMap<>();

    {
        createExchangeRate(GBP, USD, new BigDecimal("1.22"));
        createExchangeRate(USD, GBP, new BigDecimal("0.82"));
        createExchangeRate(GBP, EUR, new BigDecimal("1.10"));
        createExchangeRate(EUR, GBP, new BigDecimal("0.91"));
        createExchangeRate(EUR, USD, new BigDecimal("1.11"));
        createExchangeRate(USD, EUR, new BigDecimal("0.90"));
    }

    @Override
    public Optional<ExchangeRate> getCurrencyRate(ExchangeRateId exchangeRateId) {

        if (exchangeRateId.getFrom().equals(exchangeRateId.getTo())) {
            return Optional.of(new ExchangeRate(exchangeRateId, BigDecimal.ONE));
        }

        return Optional.ofNullable(currencyRates.get(exchangeRateId));
    }

    private void createExchangeRate(Currency from, Currency to, BigDecimal rate) {
        ExchangeRateId id = new ExchangeRateId(from, to);
        currencyRates.put(id, new ExchangeRate(id, rate));
    }
}
