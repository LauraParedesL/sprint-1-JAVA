package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccount();
    List<AccountDTO> getAllAccountDTO();
    Account getAccountById(Long id);
    AccountDTO getAccountDTObyId(Long id);
    Client getAuthenticatedClient(String email);
    boolean accountExistsByNumber(String number);
    void accountSave(Account account);
}
