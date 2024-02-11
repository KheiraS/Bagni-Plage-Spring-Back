package fr.humanbooster.fx.plages.controller.rest;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.humanbooster.fx.plages.business.Client;
import fr.humanbooster.fx.plages.dto.ClientDto;
import fr.humanbooster.fx.plages.exception.ClientInexistantException;
import fr.humanbooster.fx.plages.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class ClientRestController {

    private ClientService clientService;

    @GetMapping("clients")
    public Page<Client> getClients(@PageableDefault(page=0, size=10, sort="email") Pageable pageable) {
        return clientService.recupererClients(pageable);
    }
    
    @Operation(description="Renvoie le client dont l'id est donné en paramètre")
    @GetMapping("clients/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.recupererClient(id);
    }
  
    @PostMapping("clients")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client postClient(@Valid @RequestBody ClientDto clientDto, BindingResult result) {
    	return clientService.enregistrerClient(clientDto);
    }
    
    @Operation(description = "Met à jour complètement le client")
    @PutMapping("clients")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Client> putClient(@RequestBody ClientDto clientDto) {
        if (clientDto.getId() != null) {
            if (clientService.recupererClient(clientDto.getId()) != null) {
                Client client = clientService.enregistrerClient(clientDto);
                return ResponseEntity.status(200).body(client);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }
    
    @Operation(description = "Met à jour partiellement les infos du client")
    @PatchMapping("clients/{id}")
    public ResponseEntity<Client> patchClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        try {
            Client clientMiseAJour = clientService.mettreAJourClient(clientDto);
            return ResponseEntity.ok(clientMiseAJour);
        } catch (ClientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Log 
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("clients/{id}")
    public boolean deleteClient(@PathVariable Long id){
        return clientService.supprimerClient(id);
    }
}