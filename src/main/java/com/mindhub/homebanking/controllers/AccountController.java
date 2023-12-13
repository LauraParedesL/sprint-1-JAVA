package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")

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
}
