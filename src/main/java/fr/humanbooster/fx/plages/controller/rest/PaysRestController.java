package fr.humanbooster.fx.plages.controller.rest;

import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import fr.humanbooster.fx.plages.business.Pays;
import fr.humanbooster.fx.plages.dto.PaysDto;
import fr.humanbooster.fx.plages.exception.PaysExistantException;
import fr.humanbooster.fx.plages.exception.PaysInexistantException;
import fr.humanbooster.fx.plages.service.ClientService;
import fr.humanbooster.fx.plages.service.PaysService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/")
@AllArgsConstructor
@Validated
public class PaysRestController {

	private final PaysService paysService;
	private final ClientService clientService;

	@PostMapping("pays/{code}/{nom}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Pays postPays(@PathVariable String code, @PathVariable String nom) {
		return paysService.ajouterPays(code, nom);
	}

	/**
	 * Cette méthode renvoie la liste exhaustive des pays au format Json Spring
	 * utilise une bibliothèque qui traduit tous les objets Java en Json Cette
	 * bibliothèque s'appelle Jackson
	 * 
	 * @return une liste de pays au format Json
	 */
	@GetMapping("pays")
	public List<Pays> getPays() {
		return paysService.recupererPays();
	}
	
	@GetMapping("pays/{code}/clients")
	public List<Client> getClientsParPays(@PathVariable String code) {
		Pays pays = paysService.recupererPays(code);
		return clientService.recupererClients(pays);
	}

//	@PostMapping("pays")
//	@ResponseStatus(code = HttpStatus.CREATED)
//	public Pays postPays(@Valid @RequestBody PaysDto paysDto, BindingResult result) {
//		return paysService.ajouterPays(paysDto.getCode(), paysDto.getNom());
//	}

	@PostMapping("pays")
	@ResponseStatus(code = HttpStatus.CREATED)
	// https://sonarsource.atlassian.net/browse/RSPEC-4684
	public Pays postPays(@RequestBody Pays pays) {
		return paysService.enregistrerPays(pays);
	}

	@Operation(description = "Met à jour le nom d'un pays")
	@PatchMapping("pays/{code}/{nouveauNom}")
	public Pays patchPays(@PathVariable String code, @PathVariable String nouveauNom) {
		return paysService.mettreAJour(code, nouveauNom);
	}

	// Cette méthode n'est pas assez sécurisée !
//	@PutMapping("pays")
	// https://sonarsource.atlassian.net/browse/RSPEC-4684
//	public Pays putPays(@RequestBody Pays pays) {
//		return paysService.enregistrerPays(pays);
//	}

	@Operation(description = "Met à jour complètement le pays")
	@PutMapping("pays")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Pays> putPays(@Valid @RequestBody PaysDto paysDto, BindingResult result) {
		if (paysDto.getCode() != null) {
			if (paysService.recupererPays(paysDto.getCode()) != null) {
				Pays pays = paysService.enregistrerPays(paysDto);
				return ResponseEntity.status(200).body(pays);
			} else {
				return ResponseEntity.status(404).body(null);
			}
		} else {
			return ResponseEntity.status(400).body(null);
		}
	}

	@Operation(description = "Supprime un pays")
	@DeleteMapping("pays/{code}")
	public boolean deletePays(@PathVariable String code) {
		return paysService.supprimerPays(code);
	}

	@ExceptionHandler(PaysExistantException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public String traiterPaysExistantException(Exception e) {
		return e.getMessage();
	}

	@ExceptionHandler(PaysInexistantException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String traiterPaysInexistantException(Exception e) {
		return e.getMessage();
	}

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	public List<String> traiterDonneesInvalidesAvecDetails(ConstraintViolationException exception) {
		/*
		 * String erreur = "";
        for(ConstraintViolation<?> constraint : e.getConstraintViolations()) {
            erreur += constraint.getMessage() + "; ";
        }
        return erreur;
		 */
		
		return exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
	}

}