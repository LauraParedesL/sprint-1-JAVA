package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.CreationLoanDTO;
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

import static com.mindhub.homebanking.models.RoleType.ADMIN;

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
        Account destinationAccount = loanService.accountFindByNumber(loanApplicationDTO.getNumber());
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
            return new ResponseEntity<>("The amount can't be 0", HttpStatus.FORBIDDEN);
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

        ClientLoan clientLoan = new ClientLoan( loanApplicationDTO.getAmount() * loanDTO.getInterest() , loanApplicationDTO.getPayments() );
        Transaction loanRequest = new Transaction(TransactionType.CREDIT, LocalDate.now(), loanApplicationDTO.getAmount(), loanDTO.getName() + " " + "loan approved", destinationAccount.getBalance());

        //Actualizar la cuenta destino sumando el monto solicitado

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

    @Transactional
    @PostMapping("/loans/create")
    public ResponseEntity<Object> createLoans( @RequestBody
                                                CreationLoanDTO creationLoanDTO,
                                               Authentication authentication){
        Client client = loanService.getAuthenticatedClient(authentication.getName());
        List<Integer> payments = creationLoanDTO.getPayments();

        if (client.getRoleType() != ADMIN){
            return new ResponseEntity<>("You are not allowed to do this action", HttpStatus.FORBIDDEN);
        }
        if (creationLoanDTO.getName().isBlank()){
            return ResponseEntity.badRequest().body("Please provide data");
        }
        if(creationLoanDTO.getInterest()==0){
            return ResponseEntity.badRequest().body("interest can´t be less or equal to 0");

        }
        if (creationLoanDTO.getMaxAmount()<0){
            return ResponseEntity.badRequest().body("the max amount can't be equal to 0");
        }
        if (payments.size() == 0){
            return ResponseEntity.badRequest().body("payments can't be equal to 0");
        }
        Loan newLoan = new Loan(creationLoanDTO.getName(), creationLoanDTO.getMaxAmount(), creationLoanDTO.getPayments(), creationLoanDTO.getInterest());
        loanService.saveLoanSelected(newLoan);

        return ResponseEntity.accepted().body("created successful");
    }


}
