package fr.humanbooster.fx.plages.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.humanbooster.fx.plages.business.File;
import fr.humanbooster.fx.plages.dao.FileDao;
import fr.humanbooster.fx.plages.service.FileService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

	private FileDao fileDao;
	
	@Override
	public List<File> recupererFiles() {
		return fileDao.findAll();
	}

}
