package fr.humanbooster.fx.plages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.humanbooster.fx.plages.business.Client;
import fr.humanbooster.fx.plages.business.Pays;
import fr.humanbooster.fx.plages.dto.ClientDto;

public interface ClientService {

	/**
	 * La méthode renvoie une page de Client
	 * 
	 * @param pageable qui correspond à une demande de page
	 * @return une page de clients
	 */
	Page<Client> recupererClients(Pageable pageable);

	Client recupererClient(Long idClient);

	Client enregistrerClient(Client client);

	Client enregistrerClient(ClientDto clientDto);

	List<Client> recupererClients();

	List<Client> recupererClients(Pays pays);
	
	boolean authenticate(String email, String password);

    Client mettreAJourClient(ClientDto clientDto);

	boolean supprimerClient(Long id);
}
