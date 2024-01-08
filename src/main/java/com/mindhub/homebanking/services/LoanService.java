package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;

import java.util.List;

public interface LoanService {
    Loan getLoanById(Long id);
    Client getAuthenticatedClient(String email);
    boolean loanExistsById(Long id);
    boolean accountExistsByNumber(String number);
    Account accountFindByNumber(String number);
    void saveClientLoan(ClientLoan clientLoan);
    void saveLoanTransaction(Transaction transaction);
    void saveDestinationAccount(Account account);
    void saveLoanSelected(Loan loan);
    List<Loan> loanList();
    LoanDTO loanDTOFindById(Long id);
}
