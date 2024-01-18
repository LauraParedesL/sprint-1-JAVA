package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.dto.CardPaymentDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @PostMapping("/cards/payments")
    public ResponseEntity<Object> cardPayment(
            @RequestBody CardPaymentDTO cardPaymentDTO,
                        Authentication authentication){

        Client client = cardService.getAuthenticatedClient(authentication.getName());
        CardDTO card = cardService.cardDTOFindById(cardPaymentDTO.getCardPaymentId());
        Account account = cardService.debitAccount(cardPaymentDTO.getNumber());

        if (cardPaymentDTO.getNumber().isBlank()){
            return new ResponseEntity<>("You need to put a destination number account", HttpStatus.FORBIDDEN);
        }
        if (cardPaymentDTO.getCvv() < 100 && cardPaymentDTO.getCvv() > 999){
            return new ResponseEntity<>("cvv conteins more than 3 digits" ,HttpStatus.FORBIDDEN);
        }
        if(card.getCvv() != cardPaymentDTO.getCvv()){
            return new ResponseEntity<>("The current cvv doesn't match" , HttpStatus.FORBIDDEN);
        }
        if(cardPaymentDTO.getAmount() <= 0){
            return new ResponseEntity<>("The amount has to be greater than 0" , HttpStatus.FORBIDDEN);
        }
        if (cardPaymentDTO.getDescription().isBlank()){
            return new ResponseEntity<>("Please provide a description for the current payment" , HttpStatus.FORBIDDEN);
        }
        if(card.getToDate().isBefore(LocalDate.now())){
            return new ResponseEntity<>("This card is expired, please contact with the bank to get support" , HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() < cardPaymentDTO.getAmount()){
            return new ResponseEntity<>("insufficient founds" , HttpStatus.FORBIDDEN);
        }
        //Se debe crear una transacción que indique el débito a una de las cuentas con la descripción de la operación

        Transaction paymentTransaction = new Transaction(TransactionType.DEBIT, LocalDate.now(), cardPaymentDTO.getAmount(), cardPaymentDTO.getDescription());
        double debitAccount = (account.getBalance() - cardPaymentDTO.getAmount());

        account.setBalance(debitAccount);
        account.addTransaction(paymentTransaction);
        cardService.saveTransaction(paymentTransaction);
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
