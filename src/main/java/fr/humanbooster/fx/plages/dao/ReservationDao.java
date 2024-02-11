package fr.humanbooster.fx.plages.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.humanbooster.fx.plages.business.Client;
import fr.humanbooster.fx.plages.business.Reservation;
import fr.humanbooster.fx.plages.business.Statut;

public interface ReservationDao extends JpaRepository<Reservation, Long> {

	@Query("""
            FROM Reservation
            WHERE WEEK(dateDebut) = WEEK(current_date())
            ORDER BY dateDebut
            """)
    List<Reservation> findAllByCurrentWeek();
	
	List<Reservation> findByDateDebutBetween(LocalDate dateHeure, LocalDate dateFin);
	
	List<Reservation> findByMontantAReglerEnEurosGreaterThanAndDateDebutBetween(double seuil, LocalDate dateDebut, LocalDate dateFin);
	
	List<Reservation> findByClient(Client client);
	
	// Réservations pas encore associées à un concessionnaire
	List<Reservation> findByConcessionnaireIsNull();

	Page<Reservation> findByStatut(Pageable pageable, Statut statut);
	
}