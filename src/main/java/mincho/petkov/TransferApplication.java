package mincho.petkov;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import mincho.petkov.config.module.StubModule;
import mincho.petkov.controller.AccountApi;
import mincho.petkov.dao.AccountDao;
import mincho.petkov.model.Currency;
import mincho.petkov.model.dto.Account;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.stream.IntStream;

public class TransferApplication extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new TransferApplication().run(args);
    }


    @Override
    public void run(Configuration anagramConfig, Environment environment) {

        Injector injector = Guice.createInjector(new StubModule());

        populateAccounts(injector.getInstance(AccountDao.class));

        environment.jersey().register(injector.getInstance(AccountApi.class));

    }

    private static void populateAccounts(AccountDao accountDao) {

        Currency[] currencies = Currency.values();
        IntStream.range(1, 512)
                .mapToObj(index -> {
                    Currency currency = currencies[index % currencies.length];
                    return new Account(currency.name() + StringUtils.leftPad(String.valueOf(index), 9, '0'),
                            currency,
                            BigDecimal.valueOf(index)
                                    .multiply(BigDecimal.TEN.pow(index)));
                })
                .forEach(accountDao::save);
    }
}
