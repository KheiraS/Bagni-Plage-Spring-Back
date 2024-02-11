package fr.humanbooster.fx.plages.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.humanbooster.fx.plages.business.Parasol;
import fr.humanbooster.fx.plages.dao.ParasolDao;
import fr.humanbooster.fx.plages.exception.ParasolInexistantException;
import fr.humanbooster.fx.plages.service.ParasolService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParasolServiceImpl implements ParasolService {

	private ParasolDao parasolDao;
	
	@Override
	public Page<Parasol> recupererParasols(Pageable pageable) {
		return parasolDao.findAll(pageable);
	}

	@Override
	public Page<Parasol> recupererParasols(Pageable pageable, Long idFile) {
		// On invoque une méthode findByFileId qui est déclarée dans l'interface ParasolDao
		return parasolDao.findByFileId(pageable, idFile);
	}

	@Override
	public Parasol enregistrerParasol(Parasol parasol) {
		return parasolDao.save(parasol);
	}

	@Override
	public Parasol recupererParasol(Long idParasol) {
		return parasolDao.findById(idParasol).orElse(null);
	}

	@Override
	public List<Parasol> recupererParasols() {
		return parasolDao.findAll();
	}

	@Override
	public Parasol mettreAJourParasol(Long id, Byte nouveauNumEmplacement) {
		Parasol parasol = recupererParasol(id);
		if (parasol==null) {
			throw new ParasolInexistantException("Ce parasol n'existe pas");
		}
		parasol.setNumEmplacement(nouveauNumEmplacement);
		return enregistrerParasol(parasol);
	}

}
