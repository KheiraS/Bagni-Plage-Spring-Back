package fr.humanbooster.fx.plages.controller;

import java.util.Iterator;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.humanbooster.fx.plages.business.Parasol;
import fr.humanbooster.fx.plages.service.FileService;
import fr.humanbooster.fx.plages.service.ParasolService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ParasolController {

	private final ParasolService parasolService;
	private final FileService fileService;
	
	/**
	 * Cette méthode traite une requête HTTP utilisant la méthode GET
	 * et renvoie un objet de type ModelAndView (une Danette Pop) qui se sert de la vue parasols.jsp
	 * 
	 * @param pageable : une demande de page
	 * @param idFile : l'id de la file souhaitée (et sélectionnée sur la liste déroulante des files)
	 * @return : un objet de type ModelAndView
	 */
	@GetMapping({"/parasols"})
	public ModelAndView getParasols(
			@PageableDefault(size=8, sort={"file.numero", "numEmplacement"}) Pageable pageable,
			@RequestParam(name="ID_FILE", required = false) Long idFile) {
		
		ModelAndView mav = new ModelAndView("parasols");
		
		if (idFile==null || idFile==0) {
			Page<Parasol> pageDeParasols = parasolService.recupererParasols(pageable);
			mav.addObject("pageDeParasols", pageDeParasols);			
		}
		else {
			Page<Parasol> pageDeParasols = parasolService.recupererParasols(pageable, idFile);
			mav.addObject("pageDeParasols", pageDeParasols);
			mav.addObject("idFile", idFile);
		}
		
		Iterator<Order> iterator = pageable.getSort().iterator();
		StringBuilder sort = new StringBuilder();
		while (iterator.hasNext()) {
			sort.append(iterator.next().getProperty());
			if (iterator.hasNext()) {
				sort.append(",");
			}
		}
		mav.addObject("sort", sort.toString());
		
		mav.addObject("files", fileService.recupererFiles());
		
		return mav;
	}
	
	@GetMapping("/parasol")
	public ModelAndView getParasol(@ModelAttribute Parasol parasol,
			@RequestParam(name="ID_PARASOL", required=false) Long idParasol) {
		ModelAndView mav = new ModelAndView("parasol");
		if (idParasol!=null) {
			mav.addObject("parasol", parasolService.recupererParasol(idParasol));
		}
		mav.addObject("files", fileService.recupererFiles());
		return mav;
	}
	
	@PostMapping("/parasol")
	public ModelAndView postParasol(@Valid @ModelAttribute Parasol parasol, BindingResult result) {
		// L'objet result contient le resultat de la validation des données saisies sur le
		// formulaire HTML % aux contraintes définies dans la classe métier Parasol
		if (result.hasErrors()) {
			// Il y a eu des erreurs au moment de la validation
			// on redirige le concessionnaire vers l'URL parasol
			return getParasol(parasol, parasol.getId());
		}
		parasolService.enregistrerParasol(parasol);
		return new ModelAndView("redirect:parasols");
	}

}
