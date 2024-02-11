package fr.humanbooster.fx.plages.business;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
public class File {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private byte numero;
	
	private double prixJournalier;
	
	@OneToMany(mappedBy="file")
	@ToString.Exclude
	@JsonIgnore
	private List<Parasol> parasols;

	public File(byte numero, double prixJournalier) {
		super();
		this.numero = numero;
		this.prixJournalier = prixJournalier;
	}

}