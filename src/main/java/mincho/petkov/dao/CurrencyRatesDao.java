package mincho.petkov.dao;

import mincho.petkov.model.ExchangeRateId;
import mincho.petkov.model.ExchangeRate;

import java.util.Optional;

public interface CurrencyRatesDao {

    /**
     * This method will return the exchanged rate from the Data storage for the specific currency pair
     * @param exchangeRateId this is the currency pair
     * @return an optional of the exchange rate
     */
    Optional<ExchangeRate> getCurrencyRate(ExchangeRateId exchangeRateId);
}
