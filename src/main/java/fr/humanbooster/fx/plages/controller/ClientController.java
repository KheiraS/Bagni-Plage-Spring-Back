package fr.humanbooster.fx.plages.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import fr.humanbooster.fx.plages.business.Client;
import fr.humanbooster.fx.plages.service.ClientService;
import fr.humanbooster.fx.plages.service.LienDeParenteService;
import fr.humanbooster.fx.plages.service.PaysService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@SessionAttributes("client")
public class ClientController {

	// dépendances que Spring va instancier
	private ClientService clientService;
	private PaysService paysService;
	private LienDeParenteService lienDeParenteService;
	
	@GetMapping({"/clients"})
	public ModelAndView getClients(@PageableDefault(size=10, sort="dateHeureInscription") Pageable pageable) {
		ModelAndView mav = new ModelAndView();
		// La vue clients.jsp va être utilisée pour produire le flux HTML qui est renvoyé au navigateur
		mav.setViewName("clients");
		//pageable = PageRequest.of(0, 10, Sort.by("nom", "prenom"));
		mav.addObject("pageDeClients", clientService.recupererClients(pageable));
		String attributDeTri = pageable.getSort().iterator().next().getProperty();
		String directionDeTri = pageable.getSort().iterator().next().getDirection().name();
		System.out.println(pageable.getSort());
		mav.addObject("sort", attributDeTri + "," + directionDeTri);
		return mav;
	}
	
	@GetMapping("/client")
	public ModelAndView getClient(@RequestParam(name="ID_CLIENT", required=false) Long idClient) {
		ModelAndView mav = new ModelAndView("client");
		if (idClient!=null) {
			mav.addObject("client", clientService.recupererClient(idClient));
		}
		else {
			mav.addObject("client", new Client());
		}
		mav.addObject("pays", paysService.recupererPays());
		mav.addObject("liensDeParente", lienDeParenteService.recupererLiensDeParente());
		return mav;
	}
	
	@PostMapping("client")
	public ModelAndView postClient(@Valid @ModelAttribute Client client, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView mav = getClient(null);
			mav.addObject("client", client);
			return mav;
		}
		clientService.enregistrerClient(client);
		return new ModelAndView("redirect:/clients");
	}
	
	@GetMapping("inscription")
	public ModelAndView getInscription(@ModelAttribute("nouveauClient") Client nouveauClient) {
		ModelAndView mav = new ModelAndView("inscription");
		mav.addObject("pays", paysService.recupererPays());
		mav.addObject("liensDeParente", lienDeParenteService.recupererLiensDeParente());
		return mav;
	}
	
	@PostMapping("inscription")
	public ModelAndView postInscription(@Valid @ModelAttribute("nouveauClient") Client nouveauClient, BindingResult result) {
		if (result.hasErrors()) {
			return getInscription(nouveauClient);
		}
		clientService.enregistrerClient(nouveauClient);
		// https://www.urlencoder.org/
		return new ModelAndView("redirect:/index?notification=Client%20ajout%C3%A9%0A");
	}

}