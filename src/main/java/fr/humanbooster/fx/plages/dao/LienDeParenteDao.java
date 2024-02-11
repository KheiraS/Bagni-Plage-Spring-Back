package fr.humanbooster.fx.plages.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.humanbooster.fx.plages.business.LienDeParente;

@RepositoryRestResource
public interface LienDeParenteDao extends JpaRepository<LienDeParente, Long> {

	// Query-method
	// Requête par dérivation
	LienDeParente findByNom(String nom);

}
