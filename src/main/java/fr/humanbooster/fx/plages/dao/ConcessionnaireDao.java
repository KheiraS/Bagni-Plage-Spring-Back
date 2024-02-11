package fr.humanbooster.fx.plages.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.humanbooster.fx.plages.business.Concessionnaire;

public interface ConcessionnaireDao extends JpaRepository<Concessionnaire, Long> {

}
