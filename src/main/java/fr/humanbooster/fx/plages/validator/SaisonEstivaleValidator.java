package fr.humanbooster.fx.plages.validator;

import java.time.LocalDate;
import java.time.Month;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import fr.humanbooster.fx.plages.annotation.SaisonEstivale;
import fr.humanbooster.fx.plages.business.Reservation;

public class SaisonEstivaleValidator implements ConstraintValidator<SaisonEstivale, Reservation> {

	@Override
	public boolean isValid(Reservation reservation, ConstraintValidatorContext context) {
		if (reservation.getDateDebut() == null) {
			return false;
		}
		LocalDate start = LocalDate.of(reservation.getDateDebut().getYear(), Month.JUNE, 1);
		LocalDate end = LocalDate.of(reservation.getDateDebut().getYear(), Month.SEPTEMBER, 15);
		return (reservation.getDateDebut().equals(start) || reservation.getDateDebut().isAfter(start)) && reservation.getDateDebut().isBefore(end);
	}
}
