package fr.humanbooster.fx.plages.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Client extends Utilisateur {

	@ManyToOne
	@NotNull(message="{client.pays.manquant}")
	private Pays pays;

	@ManyToOne
	@NotNull(message="Merci de choisir un lien de parenté")
	private LienDeParente lienDeParente;
	
	@OneToMany(mappedBy="client")
	@ToString.Exclude
	@JsonIgnore
	private List<Reservation> reservations;
	
	@Column(updatable = false)
	@Builder.Default
	private LocalDateTime dateHeureInscription = LocalDateTime.now();	
	
	public Client() {
	}

	public Client(LocalDateTime dateHeureInscription) {
		super();
		this.dateHeureInscription = dateHeureInscription;
	}
		
}