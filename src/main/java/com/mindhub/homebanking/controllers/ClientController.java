package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")

public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping("/all")
    public List<ClientDTO> getAllClientDTO(){
        return clientService.getAllClientDTO();
    }

    @RequestMapping("/{id}")
    public ClientDTO getOneClientDTO(@PathVariable Long id){
        return clientService.getClientDTObyId(id);
    }

    @GetMapping("/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        List<Account> accountList = clientService.getAuthenticatedClient(authentication.getName()).getAccounts();
        List<AccountDTO> listAccountDTO = accountList.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return listAccountDTO;
    }


        @Autowired
        private PasswordEncoder passwordEncoder;


        @PostMapping("")

        public ResponseEntity<Object> register(

                @RequestParam String name,
                @RequestParam String lastName,
                @RequestParam String email,
                @RequestParam String password) {


            if (name.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {

                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

            }


            if (clientService.ClientExistsByEmail(email)) {

                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

            }


            clientService.saveClient(new Client(name, lastName, email, passwordEncoder.encode(password)));

            return new ResponseEntity<>(HttpStatus.CREATED);

        }

        @GetMapping("/current")
        public ClientDTO getClient(Authentication authentication){
            return clientService.getAuthenticatedClientDTO(authentication.getName());
        }
    }



