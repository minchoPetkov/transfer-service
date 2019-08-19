package mincho.petkov.config.module;

import com.google.inject.AbstractModule;
import mincho.petkov.controller.AccountApi;
import mincho.petkov.controller.AccountController;
import mincho.petkov.dao.AccountDao;
import mincho.petkov.dao.CurrencyRatesDao;
import mincho.petkov.dao.StubAccountDaoImpl;
import mincho.petkov.dao.StubCurrencyRatesDaoImpl;
import mincho.petkov.service.AccountService;
import mincho.petkov.service.AccountServiceImpl;
import mincho.petkov.service.TransferService;
import mincho.petkov.service.TransferServiceImpl;

public class StubModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountDao.class).to(StubAccountDaoImpl.class);
        bind(CurrencyRatesDao.class).to(StubCurrencyRatesDaoImpl.class);

        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(TransferService.class).to(TransferServiceImpl.class);

        bind(AccountApi.class).to(AccountController.class);

    }
}
