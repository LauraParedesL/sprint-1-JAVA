package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")

public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<String> createCard(@RequestParam CardType cardType,
                                                @RequestParam CardColor color,
                                                Authentication authentication) {

        Client client = cardService.getAuthenticatedClient(authentication.getName());

        if(client.getCards().stream().anyMatch(card -> card.getCardType().equals(cardType) && card.getColor().equals(color))){
            return ResponseEntity.status(403).body("You already reached the number of cards allowed");
        }

        String number = CardUtils.generateRandomCardNumber();
        int cvv = CardUtils.generateCVV();
        String cardHolder = client.getName() + " " + client.getLastName();
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusYears(5);

        Card card = new Card(cardType, number, cvv, cardHolder, fromDate, toDate, color);
        client.addCard(card);
        cardService.saveCard(card);

        return new ResponseEntity<>("New card created", HttpStatus.CREATED);
    }

    @PatchMapping("/cards/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable Long id,
                                             Authentication authentication){
        Client client = cardService.getAuthenticatedClient(authentication.getName());
        Card  card = cardService.foundCardById(id);
        boolean containsAcard = client.getCards().contains(card);
        boolean cardTrue = card.isDeleteCard();

        if (!card.getClient().getEmail().equals(client.getEmail())){
            return new ResponseEntity<>("This card doesn't match with the current client", HttpStatus.FORBIDDEN);
        }

        if (containsAcard){
            card.setDeleteCard(false);
            cardService.saveCard(card);
                    return new ResponseEntity<>("Card deleted successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("error: try again later", HttpStatus.FORBIDDEN);
        }
    }

}
