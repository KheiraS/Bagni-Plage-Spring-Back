package fr.humanbooster.fx.plages.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.humanbooster.fx.plages.business.Parasol;
import fr.humanbooster.fx.plages.exception.ParasolInexistantException;
import fr.humanbooster.fx.plages.service.ParasolService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/")
@AllArgsConstructor
public class ParasolRestController {

	private final ParasolService parasolService;

	@Operation(description="Rénvoie une page de parasols")
	@GetMapping("parasols")
	public Page<Parasol> getParasol(@PageableDefault(page=0, size=8, sort="file.numero") Pageable pageable) {
		return parasolService.recupererParasols(pageable);
	}

	// Jackson va sérialiser l'objet parasol en Json
	@Operation(description="Renvoie un parasol dont l'id est donné en paramètre")
	@GetMapping("parasols/{id}")
	public Parasol getParasol(@PathVariable Long id) {
		return parasolService.recupererParasol(id);
	}

	@Operation(description="Met à jour le numéro d'emplacement du parasol dont l'id est donné en variable de chemin")
	@PatchMapping("parasols/{id}/{nouveauNumEmplacement}")
	public Parasol patchParasol(@PathVariable Long id, @PathVariable Byte nouveauNumEmplacement) {
		return parasolService.mettreAJourParasol(id, nouveauNumEmplacement);
	}

	@ExceptionHandler(ParasolInexistantException.class)
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	public String traiterParasolInexistantException(Exception e) {
		return e.getMessage();
	}
}