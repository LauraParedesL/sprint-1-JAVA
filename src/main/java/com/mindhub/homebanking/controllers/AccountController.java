package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
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
        return accountService.getAccountDTObyId(id);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> createAccount(Authentication authentication) {

        Client client = accountService.getAuthenticatedClient(authentication.getName());

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("You can't have more than 3 accounts with this bank", HttpStatus.FORBIDDEN);
        }

        String number;
        do {
            number = AccountUtils.generateAccountNumber();

        }while(accountService.accountExistsByNumber(number));

        Account account = new Account(number, LocalDate.now(), 0D);
        client.addAccount(account);
        accountService.accountSave(account);
        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);

    }

}
