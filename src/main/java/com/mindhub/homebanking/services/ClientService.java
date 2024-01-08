package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getAllClientDTO();
    List<Client> getAllClient();
    Client getAuthenticatedClient(String email);
    ClientDTO getAuthenticatedClientDTO(String email);
    Client getClientById(Long id);
    ClientDTO getClientDTObyId(Long id);
    boolean ClientExistsByEmail(String email);
    void saveClient(Client client);


}
