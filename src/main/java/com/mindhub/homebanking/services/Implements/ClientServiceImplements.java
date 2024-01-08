package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ClientServiceImplements implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<ClientDTO> getAllClientDTO() {
        return getAllClient().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public Client getAuthenticatedClient(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ClientDTO getAuthenticatedClientDTO(String email) {
        return new ClientDTO(getAuthenticatedClient(email));
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public ClientDTO getClientDTObyId(Long id) {
        return new ClientDTO(getClientById(id));
    }

    @Override
    public boolean ClientExistsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    public void saveClient(Client client){
        clientRepository.save(client);
    }


}
