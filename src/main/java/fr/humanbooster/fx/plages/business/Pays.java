package fr.humanbooster.fx.plages.business;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity // Annotation de JPA pour demander la création d'une table pays
@Data // Lombok va générer le requiredargconstructor, getters, setters, hashCode, equals, toString
@RequiredArgsConstructor
@NoArgsConstructor
public class Pays {

	@Id
	@Column(length=2)
	@NonNull
	private String code;

	@NonNull
	@Size(min=2, message="Le nom du caractère doit comporter au moins {min} caractères")
	private String nom;
	
	@Transient
	@JsonIgnore
	private int nbClients;
	
	@OneToMany(mappedBy="pays", fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
	@ToString.Exclude
	@JsonIgnore
	private List<Client> clients;
	
	public int getNbClients() {
		return clients.size();
	}
}