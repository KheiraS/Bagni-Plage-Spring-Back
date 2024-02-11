package fr.humanbooster.fx.plages.business;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_UTILISATEUR")
@SuperBuilder
public abstract class Utilisateur {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "client_sequence")
	@SequenceGenerator(name="client_sequence", initialValue = 1000)
	protected Long id;
	
	@Column(length=80)
	protected String nom;

	protected String prenom;

	@Column(length=150)
	protected String email;

	protected String motDePasse;

	public String getNomEtPrenom() {
		return nom.toUpperCase() + " " + prenom;
	}

}
