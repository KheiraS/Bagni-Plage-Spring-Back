package fr.humanbooster.fx.plages.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.humanbooster.fx.plages.business.Client;
import fr.humanbooster.fx.plages.business.LienDeParente;
import fr.humanbooster.fx.plages.business.Pays;
import fr.humanbooster.fx.plages.util.NbInscrits;

@RepositoryRestResource
public interface ClientDao extends JpaRepository<Client, Long> {

	// Liste des clients dont le prénom est Alexis et résidant en France
	// Hibernate a généré la requête SQL suivante
	// Hibernate: select client0_.id as id2_7_, client0_.email as email3_7_, client0_.mot_de_passe as mot_de_p4_7_, client0_.nom as nom5_7_, client0_.prenom as prenom6_7_, client0_.date_heure_inscription as date_heu7_7_, client0_.lien_de_parente_id as lien_de_9_7_, client0_.pays_code as pays_co10_7_ from utilisateur client0_ cross join pays pays1_ where client0_.type_utilisateur='Client' and client0_.pays_code=pays1_.code and client0_.prenom='Alexis' and pays1_.nom='France'
	@Query("""
			FROM Client
			WHERE prenom='Alexis' AND pays.nom='France'
			""")
	List<Client> findClientsHavingFirstNameAlexisAndLivingInFrance();
	
	// Lister les clients qui se sont inscrits depuis le 01/01/2023
	@Query("""
			FROM Client
			WHERE dateHeureInscription>='2023-01-01'
			""")
			List<Client> findClientsWhoRegisteredIn2023();

	// Lister les clients espagnols
	@Query("""
			FROM Client
			WHERE pays.nom='Espagne'
			""")
	List<Client> findSpanishCustomers();
	
	// Lister les clients dont le nom de famille début par a
	@Query("FROM Client WHERE lower(nom) LIKE 'a%'")
	List<Client> findCustomersHavingNameStartingWithA();
	
	@Query("""
			FROM Client
			WHERE pays=:lePays
			""")
	List<Client> findByPays(@Param("lePays") Pays pays);
	
	// Liste des clients qui se sont inscrits entre deux dates données en paramètre
	@Query( // text block
			"""
			FROM Client
			WHERE dateHeureInscription BETWEEN :dateDebut AND :dateFin
			ORDER BY nom, prenom
			""")
	List<Client> findClientsWhoRegisteredBetween(LocalDateTime dateDebut, LocalDateTime dateFin);
	
    // Liste des clients du pays donné en paramètre qui se sont inscrits entre deux dates données en paramètre
    @Query( // text block
            """
            FROM Client
            WHERE dateHeureInscription BETWEEN :dateDebut AND :dateFin AND pays=:pays
            ORDER BY nom, prenom
            """)
    List<Client> findClientsWhoRegisteredBetweenAndLivingInGivenCountry(LocalDateTime dateDebut, LocalDateTime dateFin, Pays pays);

    // Liste des clients du pays dont le nom est donné en paramètre et qui se sont inscrits entre deux dates données en paramètre
    @Query( // text block
            """
            FROM Client
            WHERE dateHeureInscription BETWEEN :dateDebut AND :dateFin AND pays.nom=:nomPays
            ORDER BY nom, prenom
            """)
    List<Client> findClientsWhoRegisteredBetweenAndLivingInGivenCountryName(LocalDateTime dateDebut, LocalDateTime dateFin, String nomPays);

	@Query("""
			FROM Client 
			WHERE day_of_month(dateHeureInscription)=day_of_month(current_date())
			      AND month(dateHeureInscription)=month(current_date())
			      AND year(dateHeureInscription)=year(current_date())""")
	List<Client> findClientsWhoRegisteredToday();

    // Requête par dérivation (query method)
	List<Client> findAllByPays(Pays pays);

    // Requête par dérivation (query method)
	List<Client> findByPaysAndLienDeParente(Pays pays, LienDeParente lienDeParente);

	List<Client> findByPaysAndDateHeureInscriptionBetween(Pays pays, LocalDateTime dateDebut, LocalDateTime dateFin);
	
	// Utilisation de la navigabilité
	List<Client> findByPaysNomStartingWith(String debut);
	
	// Déterminer le nombre d'inscrits par mois et par année
	// Janvier 2020 : 15 inscrits
	// Février 2020 : 18 inscrits
	/**
	 * Cette méthode renvoie le nombre d'inscrits par année et par mois
	 * Elle utilise la classe util NbInscrits
	 */

	// Projection
	// Pour chaque ligne de résultat, Spring Data va créer une instance de NbInscrits
	// La requête HQL invoque le constructeur avec tous les paramètres de la classe NbInscrits
	@Query(value = """
			SELECT new fr.humanbooster.fx.plages.util.NbInscrits(year(c.dateHeureInscription), month(c.dateHeureInscription), COUNT(c.id))
			FROM Client c
			GROUP BY year(c.dateHeureInscription), month(c.dateHeureInscription) 
			ORDER BY year(c.dateHeureInscription), month(c.dateHeureInscription)
			""")
	List<NbInscrits> findNbInscrits();
	
	long deleteByPays(Pays pays);

	long deleteByPaysCode(String codePays);

	boolean existsByEmail(String string);

	//Retrouver un client par l'email
	Optional<Client> findByEmail(String email);

}