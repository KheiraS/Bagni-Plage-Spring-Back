package fr.humanbooster.fx.plages.initialisation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import fr.humanbooster.fx.plages.business.Client;
import fr.humanbooster.fx.plages.business.Concessionnaire;
import fr.humanbooster.fx.plages.business.File;
import fr.humanbooster.fx.plages.business.LienDeParente;
import fr.humanbooster.fx.plages.business.Parasol;
import fr.humanbooster.fx.plages.business.Pays;
import fr.humanbooster.fx.plages.business.Reservation;
import fr.humanbooster.fx.plages.business.Statut;
import fr.humanbooster.fx.plages.dao.ClientDao;
import fr.humanbooster.fx.plages.dao.ConcessionnaireDao;
import fr.humanbooster.fx.plages.dao.FileDao;
import fr.humanbooster.fx.plages.dao.LienDeParenteDao;
import fr.humanbooster.fx.plages.dao.ParasolDao;
import fr.humanbooster.fx.plages.dao.PaysDao;
import fr.humanbooster.fx.plages.dao.ReservationDao;
import fr.humanbooster.fx.plages.dao.StatutDao;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@AllArgsConstructor
@Log4j2
public class AjoutDonneesInitiales implements CommandLineRunner {

	private final FileDao fileDao;
	private final ParasolDao parasolDao;
	private final PaysDao paysDao;	
	private final LienDeParenteDao lienDeParenteDao;
	private final ClientDao clientDao;
	private final ConcessionnaireDao concessionnaireDao;
	private final StatutDao statutDao;
	private final ReservationDao reservationDao;
	private final PasswordEncoder passwordEncoder;
	
	private static Random random = new Random();
	
	private static Faker faker = new Faker(Locale.FRENCH);

	@Override
	public void run(String... args) throws Exception {
		ajouterFiles();
		ajouterParasols();
		ajouterPays();
		ajouterLiensDeParente();
		ajouterClients(100);
		ajouterClientFixe();
		ajouterConcessionnaire();
		ajouterStatuts();
		ajouterReservations(200);
		//afficherStatistiques();
	}
	
	private void ajouterStatuts() {
		if (statutDao.count()==0) {
			statutDao.saveAll(Arrays.asList(new Statut("en attente de traitement"),
					new Statut("acceptée"), new Statut("refusée")));			
		}
    }
	
	private void ajouterFiles() {
		// On teste si des files sont déjà en base
		if (fileDao.count()==0) {
			// il n'y a pas encore de files en base, on ajoute 8 files
			double prixJournalier = 20;
			for (byte i = 1; i <=8; i++) {
				fileDao.save(new File(i, prixJournalier));
				prixJournalier -= 2;
			}
		}
	}

	private void ajouterParasols() {
		if (parasolDao.count()==0) {
			List<File> files = fileDao.findAll();
			for (File file : files) {
				for (byte i = 1; i <=8; i++) {
					parasolDao.save(new Parasol(i, file));					
				}
			}
		}	
	}

	private void ajouterPays() {
		if (paysDao.count() == 0) {
			paysDao.saveAll(Arrays.asList(new Pays("FR", "France"), new Pays("IT", "Italie"), new Pays("GB", "Royaume-Uni"), new Pays("PT", "Portugal")));
		}
	}
	
	private void ajouterLiensDeParente() {
		if (lienDeParenteDao.count()==0) {
			lienDeParenteDao.save(new LienDeParente("Frère/Soeur", 0.5f));
			lienDeParenteDao.save(new LienDeParente("Cousin/Cousine", 0.75f));
			lienDeParenteDao.save(new LienDeParente("Aucun", 1f));
		}
	}

	public void ajouterClientFixe() {
		if (!clientDao.existsByEmail("plagiste@humanbooster.fr")) {
			List<Pays> pays = paysDao.findAll();
			LienDeParente lienDeParenteAucun = lienDeParenteDao.findByNom("Aucun");
			Client client = Client.builder().nom(faker.name().lastName())
				.prenom(faker.name().lastName())
				.pays(pays.get(random.nextInt(pays.size())))
				.email("plagiste@humanbooster.fr")
				.motDePasse(passwordEncoder.encode("12345678"))
				.lienDeParente(lienDeParenteAucun)
				.build();
			clientDao.save(client);
		}
	}
	
	public void ajouterClientsSansCondition(int nbClientsAAjouter) {
		// Partie déclarative
		List<Pays> pays = paysDao.findAll();
		LienDeParente lienDeParenteAucun = lienDeParenteDao.findByNom("Aucun");
		Map<String, Client> map = new HashMap<>();
		int compteur = 0;
		Calendar calendar = Calendar.getInstance();

		// Partie traitement
		log.info("Ajout de " + nbClientsAAjouter + " clients");
		while (map.size() != nbClientsAAjouter) {
			compteur++;
			calendar.set(2020, 1, 1);
			Date dateDebut = calendar.getTime();
			calendar = Calendar.getInstance();
			Date dateFin = calendar.getTime();
			Date dateAleatoire = faker.date().between(dateDebut, dateFin);
			calendar.setTime(dateAleatoire);
			LocalDateTime dateHeureInscription = dateAleatoire.toInstant().atZone(ZoneId.systemDefault())
					.toLocalDateTime();

			Client client = Client.builder().nom(faker.name().lastName())
					.dateHeureInscription(dateHeureInscription)
					.prenom(faker.name().lastName())
					.pays(pays.get(random.nextInt(pays.size())))
					.email(faker.internet().emailAddress())
					.motDePasse(passwordEncoder.encode("12345678"))
					.lienDeParente(lienDeParenteAucun)
					.build();
			map.put(client.getEmail(), client);
		}
		clientDao.saveAll(map.values());
		log.info("nb de clients ajoutés:" + compteur);		
		
	}

	private void ajouterClients(int nbClientsAAjouter) {
		if (clientDao.count() == 0) {
			ajouterClientsSansCondition(nbClientsAAjouter);
		}
		
	}
	
	private void ajouterConcessionnaire() {
		if (concessionnaireDao.count() == 0) {
			Concessionnaire concessionnaire = new Concessionnaire();
			concessionnaire.setNom("ROSSI");
			concessionnaire.setPrenom("Giuseppe");
			concessionnaire.setEmail("peppe@humanbooster.fr");
			concessionnaire.setMotDePasse(passwordEncoder.encode("12345678"));
			concessionnaire.setNumeroDeTelephone(faker.phoneNumber().cellPhone());
			concessionnaireDao.save(concessionnaire);
		}	
	}
	
	private void ajouterReservations(int nbReservationsAAjouter) {

		List<Client> clients = clientDao.findAll();
		List<Parasol> parasols = parasolDao.findAll();
		Statut statutEnAttente = statutDao.findByNom("en attente de traitement");

		for (int i = 0; i < nbReservationsAAjouter; i++) {
			LocalDate dateDebut = LocalDate.of(2020, random.nextInt(3) + 6, random.nextInt(30) + 1);
			LocalDate dateFin = dateDebut;
			Reservation reservation = Reservation.builder().client(
					clients.get(random.nextInt(clients.size())))
					.parasols(Arrays.asList(parasols.get(random.nextInt(parasols.size())))).dateDebut(dateDebut)
					.dateFin(dateFin).statut(statutEnAttente).build();
			reservationDao.save(reservation);			
		}
	}

	@SuppressWarnings("unused")
	private void afficherStatistiques() {

		clientDao.findSpanishCustomers().forEach(System.out::println);
		
		clientDao.findCustomersHavingNameStartingWithA().forEach(System.out::println);
		
		clientDao.findByPays(paysDao.findAll().get(0)).forEach(System.out::println);
		
		clientDao.findNbInscrits().forEach(System.out::println);
		
		clientDao.findClientsHavingFirstNameAlexisAndLivingInFrance().forEach(System.out::println);
		
		clientDao.findClientsWhoRegisteredIn2023().forEach(System.out::println);
				
		clientDao.findClientsWhoRegisteredBetween(LocalDateTime.of(2023, 2, 1, 0, 0), LocalDateTime.now())
			.forEach(c -> System.out.println(c.getNom().toUpperCase() + " " + c.getPrenom() + ", inscription le " + c.getDateHeureInscription()));
		
		clientDao.findNbInscrits().forEach(System.out::println);
		
		clientDao.findClientsWhoRegisteredToday().forEach(System.out::println);
		
		paysDao.findAllShuffled().forEach(System.out::println);
		
		paysDao.findCountriesWithoutCustomers().forEach(System.out::println);
		
		paysDao.findCountriesOrderedByNbOfCustomersDesc().forEach(System.out::println);

		File deuxiemeFile = fileDao.findAll().get(1);
		parasolDao.findByFile(deuxiemeFile).forEach(System.out::println);

	}

}