package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/clients")

public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/all")
    public List<ClientDTO> getAllClient(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @RequestMapping("/{id}")
    public ClientDTO getOneClient(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null)) ;
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


            if (clientRepository.existsByEmail(email)) {

                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

            }


            clientRepository.save(new Client(name, lastName, email, passwordEncoder.encode(password)));

            return new ResponseEntity<>(HttpStatus.CREATED);

        }

        @GetMapping("/current")
        public ClientDTO getOneClient(Authentication authentication){

            Client client =  clientRepository.findByEmail(authentication.getName());
            return new ClientDTO(client) ;
        }
    }



