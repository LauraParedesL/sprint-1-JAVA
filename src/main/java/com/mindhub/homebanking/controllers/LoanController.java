package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class LoanController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> loanRequest(
            @RequestBody LoanApplicationDTO loanApplicationDTO,
                            Authentication authentication){

        Loan loanSelected = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        Client client = clientRepository.findByEmail(authentication.getName());

        if (loanApplicationDTO.getNumber().isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getLoanId() == null) {
            return new ResponseEntity<>("not found", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 0) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() <= 0) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (!loanRepository.existsById(loanApplicationDTO.getLoanId())) {
            return new ResponseEntity<>("Not loan found", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loanSelected.getMaxAmount()) {
            return new ResponseEntity<>("Exceeds the maximum amount allowed for loan", HttpStatus.FORBIDDEN);
        }
        if (!loanSelected.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("Error: the payment selected is wrong", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.existsByNumber(loanApplicationDTO.getNumber())){
            return new ResponseEntity<>("Error: the destination account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().contains(loanApplicationDTO.getNumber())){
            return new ResponseEntity<>("Error: not match found", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(amount * % / 100 , )
    }
}
