package fr.humanbooster.fx.plages.business;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LienDeParente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nom;

	private float coefficient;

	public LienDeParente(String nom, float coefficient) {
		super();
		this.nom = nom;
		this.coefficient = coefficient;
	}
	
}