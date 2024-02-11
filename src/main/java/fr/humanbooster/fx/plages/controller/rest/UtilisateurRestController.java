package fr.humanbooster.fx.plages.controller.rest;
  
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
    
import fr.humanbooster.fx.plages.business.Utilisateur;
import fr.humanbooster.fx.plages.dto.AuthentificationDto;
import fr.humanbooster.fx.plages.service.ClientService;
import fr.humanbooster.fx.plages.service.UtilisateurService; 
          
@RestController
@RequestMapping("api/") 
public class UtilisateurRestController {
  
    @Autowired
    private UtilisateurService utilisateurService;
    
    @Autowired
    private ClientService clientService;

    
    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestBody AuthentificationDto authentificationDto) {
        // Implémente  la logique d'authentification ici, généralement via un service AuthService
        System.out.println("Controller login");
//        logger.info("Email reçu: " + clientDto.getEmail());
//        logger.info("Mot de passe reçu: " + clientDto.getMotDePasse());

		boolean isAuthenticated = clientService.authenticate(authentificationDto.getEmail(), authentificationDto.getMotDePasse());
//        logger.info("Est authentifié: " + isAuthenticated);
        System.out.println("Est authentifié: " + isAuthenticated);

        if (isAuthenticated) {
            Utilisateur utilisateur = utilisateurService.recupererUtilisateur(authentificationDto.getEmail());
            System.out.println("ID de l'utilisateur connecté: " + utilisateur.getId());
            return ResponseEntity.ok(Map.of("message", "Authentification réussie", "clientId", utilisateur.getId()));
        } else {
            return new ResponseEntity<>("Échec de l'authentification", HttpStatus.UNAUTHORIZED);
        }
    }
}    
 
