package fr.humanbooster.fx.plages.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import fr.humanbooster.fx.plages.business.Utilisateur;
import fr.humanbooster.fx.plages.service.UtilisateurService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Order(1) // Premier filtre à exécuter lorsque le serveur reçoit une requête HTTP
public class CheckPointFilter implements Filter {

	private UtilisateurService utilisateurService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// On enrichit l'objet request avec un nouvel attribut msDepart
		Date date = new Date();
		System.out.println(date + " passage dans CheckPointFilter");
		((HttpServletRequest) request).setAttribute("msDepart", date.getTime());
	
		// On récupère l'objet authentication à partir du contexte holder
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// On teste que l'utilisateur a bien passé la page de connexion
		// Spring Security instancie l'objet authentication dès que la connexion a réussi
		if (!authentication.getPrincipal().equals("anonymousUser")) {
			// On récupère l'objet User de type UserDetails (l'interface)
			// Le cast ci-dessous est nécessaire car getPrincipal renvoie un objet
			User user = (User) authentication.getPrincipal();
			// On récupère l'objet métier grâce à l'objet User de Spring Security
			Utilisateur utilisateur = utilisateurService.recupererUtilisateur(user.getUsername());
			// On enrichit l'objet request avec l'objet métier utilisateur
			// Ce faisant, toutes les JSPs auront accès à l'attribut utilisateur
			request.setAttribute("utilisateur", utilisateur);
		}			
		
		// On passe la main
		chain.doFilter(request, response);

	}

}
