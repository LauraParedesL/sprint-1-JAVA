package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplements implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDTO> getAllAccountDTO() {
        return getAllAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public AccountDTO getAccountDTObyId(Long id) {
        return new AccountDTO(getAccountById(id));
    }

    @Override
    public Client getAuthenticatedClient(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public boolean accountExistsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    @Override
    public void accountSave(Account account) {
        accountRepository.save(account);
    }
}
