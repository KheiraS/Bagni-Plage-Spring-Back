package fr.humanbooster.fx.plages.controller;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErreurController implements ErrorController {

	public static final Logger logger = LogManager.getLogger(Logger.class.getName());

	@RequestMapping("/erreur")
	public ModelAndView handleError(HttpServletRequest request) {

		Object codeRetour = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		// Si le code retour est 500 (erreur interne du serveur)
		// il est possible d'envoyer un mail à un des dev
		
		System.out.println(new Date() + " une erreur est survenue ! ");
		if (codeRetour != null) {
			System.out.println("Code retour " + codeRetour);
			logger.error(request.getRequestURI() + " : erreur : " + codeRetour + request);
		}
		ModelAndView mav = new ModelAndView("erreur");
		// J'envoie à la vue erreur.jsp un attribut codeRetour qui contient le codeRetour
		mav.addObject("codeRetour", codeRetour);
		return mav;
	}
}