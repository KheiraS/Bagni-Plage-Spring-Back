package fr.humanbooster.fx.plages.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.humanbooster.fx.plages.business.Client;
import fr.humanbooster.fx.plages.business.Pays;
import fr.humanbooster.fx.plages.dao.ClientDao;
import fr.humanbooster.fx.plages.dto.ClientDto;
import fr.humanbooster.fx.plages.exception.ClientInexistantException;
import fr.humanbooster.fx.plages.exception.SuppressionClientImpossibleException;
import fr.humanbooster.fx.plages.mapper.ClientMapper;
import fr.humanbooster.fx.plages.service.ClientService;
import fr.humanbooster.fx.plages.service.LienDeParenteService;
import fr.humanbooster.fx.plages.service.PaysService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

	private ClientDao clientDao;
	private PaysService paysService;
	private LienDeParenteService lienDeParenteService;
	private ClientMapper clientMapper;
	private PasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	@Override
	public Page<Client> recupererClients(Pageable pageable) {
		return clientDao.findAll(pageable);
	}

	@Override
	public Client recupererClient(Long idClient) {
		return clientDao.findById(idClient).orElse(null);
	}

	@Override
	public Client enregistrerClient(Client client) {
		client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
		return clientDao.save(client);
	}

	@Override
    public Client enregistrerClient(ClientDto clientDto) {
        Client client;

        // Vérifier si le clientDto a un ID (mise à jour d'un client existant)
        if (clientDto.getId() != null && clientDto.getId() > 0) {
            // Récupérer le client existant
            Optional<Client> clientExistantOpt = clientDao.findById(clientDto.getId());
            if (clientExistantOpt.isPresent()) {
                client = clientExistantOpt.get();
                // Mettre à jour les informations du client
                client.setNom(clientDto.getNom());
                client.setPrenom(clientDto.getPrenom());
                client.setEmail(clientDto.getEmail());
                client.setPays(paysService.recupererPays(clientDto.getPaysDto().getCode()));
                client.setLienDeParente(lienDeParenteService.recupererLienDeParente(clientDto.getLienDeParenteDto().getId()));

                // Mettre à jour le mot de passe uniquement s'il est fourni
                if (clientDto.getMotDePasse() != null && !clientDto.getMotDePasse().trim().isEmpty() && 
                        !passwordEncoder.matches(clientDto.getMotDePasse(), client.getMotDePasse())) {
                    client.setMotDePasse(passwordEncoder.encode(clientDto.getMotDePasse()));
                }
            } else {
                // Si le client n'existe pas, renvoyer une exception ou gérer l'erreur
                throw new ClientInexistantException("Le client avec l'ID " + clientDto.getId() + " n'existe pas");
            }
        }
else {
            // Pour un nouveau client, créer une nouvelle instance Client et cryptez le mot de passe
            client = new Client();
            client.setNom(clientDto.getNom());
            client.setPrenom(clientDto.getPrenom());
            client.setEmail(clientDto.getEmail());
            client.setMotDePasse(passwordEncoder.encode(clientDto.getMotDePasse()));
            client.setPays(paysService.recupererPays(clientDto.getPaysDto().getCode()));
            client.setLienDeParente(lienDeParenteService.recupererLienDeParente(clientDto.getLienDeParenteDto().getId()));
        }

        return clientDao.save(client);
    }

	@Override
    public boolean supprimerClient(Long id) {
		Client client = recupererClient(id);
		if (client==null) {
			throw new ClientInexistantException("Ce client n'existe pas");
		}
		if (!client.getReservations().isEmpty()) {
			throw new SuppressionClientImpossibleException("Le client ne peut être supprimé car il a effectué des réservations");
		}
        clientDao.delete(client);
        return true;
    }

	@Override
	public List<Client> recupererClients() {
		return clientDao.findAll();
	}

	@Override
	public List<Client> recupererClients(Pays pays) {
		return clientDao.findByPays(pays);
	}
	@Override
    public boolean authenticate(String email, String password) {
        Optional<Client> client = clientDao.findByEmail(email);
        if (client.isPresent()) {
            return passwordEncoder.matches(password, client.get().getMotDePasse());
        }
        return false;
    }
	  @Override
	    public Client mettreAJourClient(ClientDto clientDto) {
	        Client client = clientDao.findById(clientDto.getId())
	            .orElseThrow(() -> new ClientInexistantException("Client non trouvé avec l'ID : " + clientDto.getId()));

	        client.setNom(clientDto.getNom());
	        client.setPrenom(clientDto.getPrenom());
	        client.setEmail(clientDto.getEmail());
	        client.setPays(paysService.recupererPays(clientDto.getPaysDto().getCode()));
	        //client.setLienDeParente(lienDeParenteService.recupererLienDeParente(clientDto.getLienDeParenteDto().getId()));
	        if (clientDto.getLienDeParenteDto() != null) {
	            client.setLienDeParente(lienDeParenteService.recupererLienDeParente(clientDto.getLienDeParenteDto().getId()));
	        }
	        if (clientDto.getMotDePasse() != null && !clientDto.getMotDePasse().trim().isEmpty() && 
	                !passwordEncoder.matches(clientDto.getMotDePasse(), client.getMotDePasse())) {
	                client.setMotDePasse(passwordEncoder.encode(clientDto.getMotDePasse()));
	            }

	        return clientDao.save(client);
	    }

}
