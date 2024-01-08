package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")

public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<String> createAccount(@RequestParam CardType cardType,
                                                @RequestParam CardColor color,
                                                Authentication authentication) {

        Client client = cardService.getAuthenticatedClient(authentication.getName());

        if(client.getCards().stream().anyMatch(card -> card.getCardType().equals(cardType) && card.getColor().equals(color))){
            return ResponseEntity.status(403).body("You already reached the number of cards allowed");
        }



        String number = generateRandomCardNumber();
        int cvv = (int)(Math.random() * 999 + 100);
        String cardHolder = client.getName() + " " + client.getLastName();
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusYears(5);

        Card card = new Card(cardType, number, cvv, fromDate, toDate, color);
        client.addCard(card);
        cardService.safeCard(card);


        return new ResponseEntity<>("New card created", HttpStatus.CREATED);
    }


    private String generateRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int section = (int) (Math.random() * 9000 + 1000);
            cardNumber.append(section).append("-");
        }
        return cardNumber.substring(0, cardNumber.length() - 1);
    }
}
