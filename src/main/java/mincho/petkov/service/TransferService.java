package mincho.petkov.service;

import mincho.petkov.model.Transfer;

public interface TransferService {

    /**
     * Make transfer between accounts
     * @param transfer a transfer request
     * @throws IllegalStateException
     */
    void makeTransfer(Transfer transfer) throws IllegalStateException;

}
