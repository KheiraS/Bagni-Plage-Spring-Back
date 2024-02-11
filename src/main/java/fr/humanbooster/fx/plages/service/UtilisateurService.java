package fr.humanbooster.fx.plages.service;

import fr.humanbooster.fx.plages.business.Utilisateur;

public interface UtilisateurService {

	Utilisateur recupererUtilisateur(String email, String motDePasse);

	Utilisateur recupererUtilisateur(Long idUtilisateur);

	Utilisateur recupererUtilisateur(String email);
}
