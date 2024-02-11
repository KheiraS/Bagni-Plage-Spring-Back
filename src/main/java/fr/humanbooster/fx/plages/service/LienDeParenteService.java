package fr.humanbooster.fx.plages.service;

import java.util.List;

import fr.humanbooster.fx.plages.business.LienDeParente;

public interface LienDeParenteService {

	List<LienDeParente> recupererLiensDeParente();

	LienDeParente recupererLienDeParente(Long id);

	LienDeParente ajouterLienDeParente(String nom, float coefficient);

	LienDeParente enregistrerLienDeParente(LienDeParente lienDeParente);

	boolean supprimerLienDeParente(LienDeParente lienDeParente);

}