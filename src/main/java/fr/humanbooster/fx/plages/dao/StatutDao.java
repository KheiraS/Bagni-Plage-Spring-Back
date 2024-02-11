package fr.humanbooster.fx.plages.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.humanbooster.fx.plages.business.Statut;

public interface StatutDao extends JpaRepository<Statut, Long> {

	Statut findByNom(String nom);

}
