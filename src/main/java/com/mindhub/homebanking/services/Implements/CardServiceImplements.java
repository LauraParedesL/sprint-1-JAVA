package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.dto.CardPaymentDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImplements implements CardService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public Client getAuthenticatedClient(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card foundCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public Card findCardByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public Account debitAccount(String number) {
        return accountRepository.findByNumber(number) ;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
         transactionRepository.save(transaction);
    }

}
