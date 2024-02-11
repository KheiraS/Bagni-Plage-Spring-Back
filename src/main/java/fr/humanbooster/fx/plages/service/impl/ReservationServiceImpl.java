package fr.humanbooster.fx.plages.service.impl;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.humanbooster.fx.plages.business.Client;
import fr.humanbooster.fx.plages.business.Concessionnaire;
import fr.humanbooster.fx.plages.business.Reservation;
import fr.humanbooster.fx.plages.business.Statut;
import fr.humanbooster.fx.plages.dao.ReservationDao;
import fr.humanbooster.fx.plages.service.ReservationService;
import lombok.AllArgsConstructor;

/**
 * 
 */
@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private ReservationDao reservationDao;
	
    /**
     * @param Reservation reservation 
     * @return
     */
    @Override
	public Reservation enregistrerReservation(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    /**
     * @param Long id 
     * @return
     */
    @Override
	public Reservation recupererReservation(Long id) {
        return reservationDao.findById(id).orElse(null);
    }

    /**
     * @param Client client 
     * @return
     */
    @Override
	public List<Reservation> recupererReservations(Client client) {
        return reservationDao.findByClient(client);
    }

    /**
     * @param Pageable pageable 
     * @return
     */
    @Override
	public Page<Reservation> recupererReservations(Pageable pageable) {
        return reservationDao.findAll(pageable);
    }

    /**
     * @param Pageable pageable 
     * @param Statut statut 
     * @return
     */
    @Override
	public Page<Reservation> recupererReservations(Pageable pageable, Statut statut) {
        return reservationDao.findByStatut(pageable, statut);
    }

    /**
     * @return
     */
    @Override
	public List<Reservation> recupererReservations() {
        return reservationDao.findAll();
    }

    /**
     * @param Reservation reservation 
     * @param Concessionnaire concessionnaire 
     * @return
     */
    @Override
	public Reservation traiterReservation(Reservation reservation, Concessionnaire concessionnaire) {
    	reservation.setConcessionnaire(concessionnaire);
    	return enregistrerReservation(reservation);
    }

	@Override
	public List<Reservation> recupererReservationsDeLaSemaineEnCours() {
		return reservationDao.findAllByCurrentWeek();
	}

}