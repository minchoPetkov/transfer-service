package mincho.petkov.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import mincho.petkov.controller.mapper.AccountMapper;
import mincho.petkov.controller.mapper.TransferRequestMapper;
import mincho.petkov.model.Transfer;
import mincho.petkov.model.dto.Account;
import mincho.petkov.model.dto.AccountDetail;
import mincho.petkov.model.dto.TransferRequest;
import mincho.petkov.service.AccountService;
import mincho.petkov.service.TransferService;

import javax.ws.rs.core.Response;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Singleton
public class AccountController implements AccountApi {

    private final TransferService transferService;
    private final AccountService accountService;

    @Inject
    public AccountController(TransferService transferService, AccountService accountService) {
        this.transferService = transferService;
        this.accountService = accountService;
    }

    @Override
    public List<AccountDetail> getAll() {
        return accountService.getAllAccounts().stream()
                .map(AccountMapper::mapped)
                .sorted(Comparator.comparing(AccountDetail::getAccountNumber))
                .collect(toList());
    }

    @Override
    public Response getById(String id) {
        Optional<AccountDetail> accountDetail = accountService.getByAccountNumber(id).map(AccountMapper::mapped);
        if (!accountDetail.isPresent()) {
            return Response.status(NOT_FOUND).build();
        }

        return Response.ok(accountDetail.get()).build();

    }

    @Override
    public Response makeTransfer(TransferRequest transferRequest) {

        Optional<Account> from = accountService.getByAccountNumber(transferRequest.getAccountFrom());
        Optional<Account> to = accountService.getByAccountNumber(transferRequest.getAccountTo());
        Transfer transfer = null;
        try {
            transfer = TransferRequestMapper.mapped(transferRequest.getAmount(), from, to);

            transferService.makeTransfer(transfer);
        } catch (IllegalArgumentException ex) {
            return Response.status(BAD_REQUEST).build();
        } catch (IllegalStateException ex) {
            Account fromAccount = transfer.getAccountFrom();
            String message = "Not enough funds for account: " + fromAccount.getAccountNumber() + " Available resources: " + fromAccount.getCurrency() + " " + fromAccount.getAccountBalance() + " truing to transfer: " + transfer.getAmount();
            return Response.status(BAD_REQUEST).entity(message).build();
        }
        return Response.ok().build();
    }

}
