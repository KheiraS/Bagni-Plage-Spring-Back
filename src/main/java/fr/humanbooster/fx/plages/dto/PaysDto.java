package fr.humanbooster.fx.plages.dto;

import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class PaysDto {

	@Size(min=2, max=2, message="Le code du pays doit comporter {min} caractères")
	String code;
	String nom;
}
