package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")

public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/all")
    public List<AccountDTO> getAllAccount() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }
   @RequestMapping("{id}")
    public AccountDTO getOneAccount(@PathVariable Long id){
        return new AccountDTO(accountRepository.findById(id).orElse(null)) ;
    }

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> createAccount(Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("You can't have more than 3 accounts with this bank", HttpStatus.FORBIDDEN);
        }

        String number;
        do {
            number = "VIN-" + getRandomNumber(10000000, 99999999);

        }while(accountRepository.existsByNumber(number));

        Account account = new Account(number, LocalDate.now(), 0D);
        client.addAccount(account);
        accountRepository.save(account);
        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
