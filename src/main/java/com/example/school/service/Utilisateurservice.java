package com.example.school.service;

import com.example.school.model.Utilisateur;
import com.example.school.repository.UtilisateurRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class Utilisateurservice {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public String tenterConnexion(String nom, String prenom, HttpSession session) {
        
        
        Optional<Utilisateur> userOpt = utilisateurRepository.findByNomAndPrenom(nom, prenom);

        Utilisateur utilisateur;

        if (userOpt.isPresent()) {
            
            utilisateur = userOpt.get();
         
        } else {
            utilisateur = new Utilisateur();
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            
    
            utilisateurRepository.save(utilisateur);
        }

        session.setAttribute("utilisateurConnecte", utilisateur);
        
        session.setMaxInactiveInterval(200); 

        return "SUCCES";
    }
}