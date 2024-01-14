package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

public interface CardService {

    Client getAuthenticatedClient(String email);
    void saveCard(Card card);

    Card getCardNumber(String number);
}
