package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;

public interface CardService {

    Client getAuthenticatedClient(String email);
    void saveCard(Card card);
    Card foundCardById(Long id);
    CardDTO cardDTOFindById(Long id);
    Account debitAccount(String number);
    Transaction saveTransaction(Transaction transaction);
}
