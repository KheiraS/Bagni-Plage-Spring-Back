package fr.humanbooster.fx.plages.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fr.humanbooster.fx.plages.business.Utilisateur;
import fr.humanbooster.fx.plages.service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@AllArgsConstructor
@Log4j2
public class UtilisateurController {

	private final UtilisateurService utilisateurService;
	private final HttpSession httpSession;
	
	protected static final String DOSSIER_IMAGES = "src/main/webapp/images/";
	
	@SuppressWarnings("unused")
	private Utilisateur recupererUtilisateurDuContexte() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			User user = (User) authentication.getPrincipal();
			return utilisateurService.recupererUtilisateur(user.getUsername());
		}
		else {
			return null;
		}
	}
	
	// Une méthode du contrôleur peut prendre en charge plusieurs URL
	// Dans le cas de cette méthode, les URL prises en charge sont / et index
	@GetMapping(value = { "/", "index"})
	public ModelAndView getConnexion() {		
		//System.out.println(new Date() + " dans getConnexion()");
		log.info(new Date() + " dans getConnexion()");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");		
		return mav;
	}
/*	
 * Méthode devenue caduque avec l'utilisation de Spring Secuity :)
	@PostMapping("/connexion")
	public ModelAndView postConnexion(@RequestParam("EMAIL") String email, @RequestParam("MOT_DE_PASSE") String motDePasse) {
		System.out.println(new Date() + " dans postConnexion()");
		Utilisateur utilisateur = utilisateurService.recupererUtilisateur(email, motDePasse);
		if (utilisateur==null) {
			ModelAndView mav = new ModelAndView("redirect:index");
			mav.addObject("notification", "Mot de passe et/ou email incorrect");
			return mav;
		}
		else {
			// On ajoute en session HTTP l'objet utilisateur
			httpSession.setAttribute("utilisateur", utilisateur);
			ModelAndView mav = new ModelAndView("redirect:clients");
			return mav;
		}
	}
	*/
	
	@GetMapping("televerserAvatar")
	public ModelAndView getAvatarUtilisateur(@RequestParam(name="ID_UTILISATEUR") Long idUtilisateur) {
		ModelAndView mav = new ModelAndView("televersementAvatar");
		mav.addObject("utilisateur", utilisateurService.recupererUtilisateur(idUtilisateur));
		return mav;
	}
	
	@PostMapping("televersementAvatar")
	public ModelAndView postAvatarUtilisateur(@RequestParam("FICHIER") MultipartFile fichier, @RequestParam(name="ID_UTILISATEUR") Long idUtilisateur) {
		try {
			enregisterFichier(idUtilisateur + ".jpg", fichier);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:clients");
	}
	
	@GetMapping("deconnexion")
	public ModelAndView getDeconnexion() {
		httpSession.invalidate();
		ModelAndView mav = new ModelAndView("redirect:/index");
		mav.addObject("notification", "Au revoir");
		return mav;
	}
	
	protected static void enregisterFichier(String nom, MultipartFile multipartFile) throws IOException {
		Path chemin = Paths.get(DOSSIER_IMAGES);

		if (!Files.exists(chemin)) {
			Files.createDirectories(chemin);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path cheminFichier = chemin.resolve(nom);
			Files.copy(inputStream, cheminFichier, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Erreur d'écriture : " + nom, ioe);
		}
	}
}
