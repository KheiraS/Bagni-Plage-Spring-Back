package fr.humanbooster.fx.plages.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.humanbooster.fx.plages.business.Utilisateur;

public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {

	boolean existsByEmail(String email);

	Utilisateur findLastByEmailAndMotDePasse(String email, String motDePasse);

	Utilisateur findByEmail(String username);
}
