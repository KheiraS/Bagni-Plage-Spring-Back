package fr.humanbooster.fx.plages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.humanbooster.fx.plages.business.Parasol;

public interface ParasolService {

	Page<Parasol> recupererParasols(Pageable pageable);

	/**
	 * Cette méthode renvoie une page de parasols qui se trouvent sur
	 * la file dont l'id est donné en paramètre
	 * 
	 * @param pageable
	 * @param idFile
	 * @return
	 */
	Page<Parasol> recupererParasols(Pageable pageable, Long idFile);

	Parasol enregistrerParasol(Parasol parasol);

	Parasol recupererParasol(Long idParasol);

	List<Parasol> recupererParasols();

	Parasol mettreAJourParasol(Long id, Byte nouveauNumEmplacement);

}
