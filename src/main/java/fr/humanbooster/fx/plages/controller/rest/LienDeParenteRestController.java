package fr.humanbooster.fx.plages.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.humanbooster.fx.plages.business.LienDeParente;
import fr.humanbooster.fx.plages.service.LienDeParenteService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/") // L'API des LienDeParente sera isolée sur une URL qui débute par api
public class LienDeParenteRestController {

	private LienDeParenteService lienDeParenteService;

	@PostMapping("lienParente/{nom}/{coefficient}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LienDeParente postLienDeParente(@PathVariable String nom, @PathVariable float coefficient) {
		return lienDeParenteService.ajouterLienDeParente(nom, coefficient);
	}

	@GetMapping("liendeparente")
	public List<LienDeParente> getLiensDeParente() {
		return lienDeParenteService.recupererLiensDeParente();
	}

	@GetMapping("liendeparente/{id}")
	public LienDeParente getLienDeParente(@PathVariable Long id) {
		return lienDeParenteService.recupererLienDeParente(id);
	}

	@PutMapping("liendeparente/{id}/{nouveauNom}/{nouveauCoefficient}")
	public LienDeParente putLienDeParente(@PathVariable Long id, @PathVariable(name = "nouveauNom") String nom,
			@PathVariable(name = "nouveauCoefficient") float coefficient) {
		LienDeParente lienDeParente = lienDeParenteService.recupererLienDeParente(id);
		lienDeParente.setNom(nom);
		lienDeParente.setCoefficient(coefficient);
		return lienDeParenteService.enregistrerLienDeParente(lienDeParente);
	}

	@PatchMapping("liendeparente/{id}/{nouveauNom}")
	public LienDeParente patchLienDeParente(@PathVariable Long id, @PathVariable(name = "nouveauNom") String nom) {
		LienDeParente lienDeParente = lienDeParenteService.recupererLienDeParente(id);
		lienDeParente.setNom(nom);
		return lienDeParenteService.enregistrerLienDeParente(lienDeParente);
	}

	@DeleteMapping("liendeparente/{id}")
	public boolean deleteLienDeParente(@PathVariable Long id) {
		LienDeParente lienDeParente = lienDeParenteService.recupererLienDeParente(id);
		return lienDeParenteService.supprimerLienDeParente(lienDeParente);
	}
}