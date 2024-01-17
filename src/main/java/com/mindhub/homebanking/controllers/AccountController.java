package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")

public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/all")
    public List<AccountDTO> getAllAccountDTO() {
        return accountService.getAllAccountDTO();
    }
    @GetMapping("{id}")
    public AccountDTO getOneAccount(@PathVariable Long id){

        if(accountService.getAccountById(id).isActiveAccount()){
            return accountService.getAccountDTObyId(id);
        }else {
            return null;
        }
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> createAccount(@RequestParam AccountType accountType,
                                                Authentication authentication) {

        Client client = accountService.getAuthenticatedClient(authentication.getName());

        if (client.getAccounts().size() >= 5) {
            return new ResponseEntity<>("You can't have more than 5 accounts with this bank", HttpStatus.FORBIDDEN);
        }

        String number;
        do {
            number = AccountUtils.generateAccountNumber();

        }while(accountService.accountExistsByNumber(number));

        Account account = new Account(number, LocalDate.now(), 0D, accountType);
        client.addAccount(account);
        accountService.accountSave(account);
        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);

    }

    @PatchMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id,
                                             Authentication authentication) {
        Client client = accountService.getAuthenticatedClient(authentication.getName());
        Account account = accountService.getAccountById(id);
        boolean hasThisAccount = client.getAccounts().contains(account);


        if (!account.getClient().getEmail().equals(client.getEmail())) {
            return new ResponseEntity<>("This account doesn't match with the current client", HttpStatus.FORBIDDEN);
        }

        if (hasThisAccount) {
            account.setActiveAccount(false);
            accountService.accountSave(account);
            if (account.getBalance() != 0) {
                return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("error: an account with balance greater than 0 can't be deleted", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("error: try again later", HttpStatus.FORBIDDEN);
        }
    }


}
