package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<String> createTransaction(@RequestParam Double amount,
                                                    @RequestParam String description,
                                                    @RequestParam String originAccount,
                                                    @RequestParam String destinationAccount,
                                                    Authentication authentication){

        if (amount <= 0){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (description.isBlank() || originAccount.isBlank() || destinationAccount.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        Client client = transactionService.getAuthenticatedClient(authentication.getName());
        Account accountOrigin = transactionService.accountFindByNumber(originAccount);
        Account accountDestination = transactionService.accountFindByNumber(destinationAccount);


        if (accountOrigin.equals(accountDestination)){
            return new ResponseEntity<>("The account number cannot have same number" , HttpStatus.FORBIDDEN);
        }

        if (accountOrigin == null){
            return new ResponseEntity<>("This account doesn´t exist, please try again", HttpStatus.FORBIDDEN);
        }

        if (accountDestination == null){
            return new ResponseEntity<>("This account doesn´t exist, please try again", HttpStatus.FORBIDDEN);
        }

        if (!accountOrigin.getClient().getEmail().equals(client.getEmail())){
            return new ResponseEntity<>("This account doesn't match with the current client", HttpStatus.FORBIDDEN);
        }

        if (accountOrigin.getBalance() < amount){
            return new ResponseEntity<>("insufficient funds", HttpStatus.FORBIDDEN);
        }

        Transaction transaction1 = new Transaction(TransactionType.DEBIT, LocalDate.now(), -amount, description);
        Transaction transaction2 = new Transaction(TransactionType.CREDIT, LocalDate.now(), amount, description);

        accountOrigin.addTransaction(transaction1);
        accountDestination.addTransaction(transaction2);
        transactionService.saveTransaction(transaction1);
        transactionService.saveTransaction(transaction2);
        accountOrigin.setBalance(accountOrigin.getBalance() - amount);
        accountDestination.setBalance(accountDestination.getBalance() + amount);
        transactionService.saveAccounts(accountOrigin);
        transactionService.saveAccounts(accountDestination);



        return new ResponseEntity<>("Transaction done succesfully", HttpStatus.CREATED);
    }


}
