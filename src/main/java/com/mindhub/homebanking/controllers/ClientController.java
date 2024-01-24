package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")

public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/all")
    public List<ClientDTO> getAllClientDTO(){
        return clientService.getAllClientDTO();
    }

    @GetMapping("/{id}")
    public ClientDTO getOneClientDTO(@PathVariable Long id){
        return clientService.getClientDTObyId(id);
    }

    @GetMapping("/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        List<Account> accountList = clientService.getAuthenticatedClient(authentication.getName()).getAccounts();
        List<AccountDTO> accountDTO = accountList.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return accountDTO;
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

            Client client = new Client(name, lastName, email, passwordEncoder.encode(password));

            String number;
            do {
                number= AccountUtils.generateAccountNumber();

            } while (accountService.accountExistsByNumber(number));
            Account account= new Account(number, LocalDate.now(),0.0, AccountType.SAVINGS);
            clientService.saveClient(client);
            client.addAccount(account);
            accountService.accountSave(account);

            return new ResponseEntity<>("Successful", HttpStatus.CREATED);
        }

        @GetMapping("/current")
        public ClientDTO getClient(Authentication authentication){
            return clientService.getAuthenticatedClientDTO(authentication.getName());
        }
    }



