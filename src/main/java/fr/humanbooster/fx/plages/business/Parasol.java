package fr.humanbooster.fx.plages.business;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Parasol {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private byte numEmplacement;

	@ManyToOne
	@NotNull(message="{parasol.file.manquante}")
	private File file;
	
	@ToString.Exclude
	@ManyToMany(mappedBy="parasols")
	@JsonIgnore
	private List<Reservation> reservations;

	public Parasol(byte numEmplacement, File file) {
		super();
		this.numEmplacement = numEmplacement;
		this.file = file;
	}

	@JsonIgnore
	public String getNumeroDeLaFile() {
		return String.valueOf(file.getNumero());
	}
	
	public String getNumeroEmplacementEtNumeroDeFile() {
		return numEmplacement + " en file " + file.getNumero();
	}
}