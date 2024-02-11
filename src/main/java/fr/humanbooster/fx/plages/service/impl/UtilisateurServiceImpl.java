package fr.humanbooster.fx.plages.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.humanbooster.fx.plages.business.Concessionnaire;
import fr.humanbooster.fx.plages.business.Utilisateur;
import fr.humanbooster.fx.plages.dao.UtilisateurDao;
import fr.humanbooster.fx.plages.service.UtilisateurService;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {

	private final UtilisateurDao utilisateurDao;
	
	@Override
	@Transactional(readOnly=true)
	public Utilisateur recupererUtilisateur(String email, String motDePasse) {
		return utilisateurDao.findLastByEmailAndMotDePasse(email, motDePasse);
	}

	@Override
	public Utilisateur recupererUtilisateur(Long idUtilisateur) {
		return utilisateurDao.findById(idUtilisateur).orElse(null);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}

		Utilisateur utilisateur = utilisateurDao.findByEmail(username);
		if (utilisateur == null) {
			throw new UsernameNotFoundException("user " + username + " not found");
		}
		List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(utilisateur);
		
		// On hydrate un objet de type User à l'aide des données de l'objet métier utilisateur
		
		//User user = new User(utilisateur.getEmail(), utilisateur.getMotDePasse(), grantedAuthorities);
		
		// Utilisation du patron builder
		UserDetails user = User.builder()
				.username(utilisateur.getEmail())
				.password(utilisateur.getMotDePasse())
				.authorities(grantedAuthorities)
				.build();
		
		return user;
	}
	
    private List<GrantedAuthority> getGrantedAuthorities(Utilisateur utilisateur) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //utilisateur instanceof Concessionnaire
        // est l'équivalent de : 
        //utilisateur.getClass().getSimpleName().equals("Concessionnaire")
        if(utilisateur instanceof Concessionnaire) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));	
        } else {
        	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));	
        }
        return authorities;
    }

	@Override
	public Utilisateur recupererUtilisateur(String email) {
		return utilisateurDao.findByEmail(email);
	}


}
