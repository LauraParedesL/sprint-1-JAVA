package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;

public interface TransactionService {
    Client getAuthenticatedClient(String email);
    Account accountFindByNumber (String number);
    void saveTransaction(Transaction transaction);
    void saveAccounts(Account account);
}
