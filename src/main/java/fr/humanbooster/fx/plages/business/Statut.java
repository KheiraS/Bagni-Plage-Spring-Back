package fr.humanbooster.fx.plages.business;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Statut {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String nom;

	// Equivalent à @RequiredArgsConstructor
//	public Statut(@NonNull String nom) {
//		super();
//		this.nom = nom;
//	}
	
}