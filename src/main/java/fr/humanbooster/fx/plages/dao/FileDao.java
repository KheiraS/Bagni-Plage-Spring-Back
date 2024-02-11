package fr.humanbooster.fx.plages.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.humanbooster.fx.plages.business.File;

@RepositoryRestResource
public interface FileDao extends JpaRepository<File, Long> {

	List<File> findByNumero(byte numero);
	
	List<File> findByPrixJournalierLessThanEqual(double seuil);
}
