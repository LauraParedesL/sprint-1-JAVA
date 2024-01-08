package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImplements implements LoanService {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public Client getAuthenticatedClient(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public boolean loanExistsById(Long id) {
        return loanRepository.existsById(id);
    }

    @Override
    public boolean accountExistsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    @Override
    public Account accountFindByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public void saveLoanTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void saveDestinationAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void saveLoanSelected(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public List<Loan> loanList() {
        return loanRepository.findAll();
    }

    @Override
    public LoanDTO loanDTOFindById(Long id) {
        return loanRepository.findById(id).map(LoanDTO::new).orElse(null);
    }


}
