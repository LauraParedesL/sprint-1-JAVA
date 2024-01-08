package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanService;
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

public class LoanController {
    @Autowired
    LoanService loanService;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> loanRequest(
            @RequestBody LoanApplicationDTO loanApplicationDTO,
                            Authentication authentication) {

        Loan loanSelected = loanService.getLoanById(loanApplicationDTO.getLoanId());
        Client client = loanService.getAuthenticatedClient(authentication.getName());
        LoanDTO loanDTO = loanService.loanDTOFindById(loanApplicationDTO.getLoanId());

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
        if (!loanService.loanExistsById(loanApplicationDTO.getLoanId())) {
            return new ResponseEntity<>("Not loan found", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loanSelected.getMaxAmount()) {
            return new ResponseEntity<>("Exceeds the maximum amount allowed for loan", HttpStatus.FORBIDDEN);
        }
        if (!loanSelected.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("Error: the payment selected is wrong", HttpStatus.FORBIDDEN);
        }
        if (!loanService.accountExistsByNumber(loanApplicationDTO.getNumber())){
            return new ResponseEntity<>("Error: the destination account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().contains(loanApplicationDTO.getNumber())){
            return new ResponseEntity<>("Error: not match found", HttpStatus.FORBIDDEN);
        }
        if (client.getClientLoans().stream().anyMatch(clientLoan -> clientLoan.getLoan().getId().equals(loanSelected.getId()))){
            return new ResponseEntity<>("Error: Client already has this kind of loan" , HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan( loanApplicationDTO.getAmount() * 1.2 , loanApplicationDTO.getPayments() );
        Transaction loanRequest = new Transaction(TransactionType.CREDIT, LocalDate.now(), loanApplicationDTO.getAmount(), loanDTO.getName() + " " + "loan approved");

        //Actualizar la cuenta destino sumando el monto solicitado
        Account destinationAccount = loanService.accountFindByNumber(loanApplicationDTO.getNumber());
        double destinationAccountUpdated = (destinationAccount.getBalance() + loanApplicationDTO.getAmount());
        destinationAccount.setBalance(destinationAccountUpdated);

        destinationAccount.addTransaction(loanRequest);
        loanService.saveLoanTransaction(loanRequest);
        client.addClientLoan(clientLoan);
        loanSelected.addClientLoan(clientLoan);
        loanService.saveClientLoan(clientLoan);



        return new ResponseEntity<>("Loan successfully pre-approved", HttpStatus.CREATED);

    }

    @GetMapping("/loans")
    public List<LoanDTO> loanDTOList(){
        return loanService.loanList().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

}
