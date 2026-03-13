package com.example.school.controller;

import com.example.school.model.Question;
import com.example.school.model.Utilisateur;
import com.example.school.service.QuestionService;
import com.example.school.service.Utilisateurservice;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {

    @Autowired
    private Utilisateurservice utilisateurService;

    @Autowired
    private QuestionService questionService;

    
    @GetMapping("/") 
    public String showIndexPage(HttpSession session) {
        if (session.getAttribute("utilisateurConnecte") != null) {
            return "redirect:/choix-quiz";
        }
        return "index"; 
    }

 
    @PostMapping("/login")
    public String login(@RequestParam String nom, 
                        @RequestParam String prenom, 
                        HttpSession session) {
        utilisateurService.tenterConnexion(nom, prenom, session);
        return "redirect:/choix-quiz";
    }

    
    @GetMapping("/choix-quiz")
    public String afficherChoix(HttpSession session, Model model) {
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (user == null) return "redirect:/"; 

        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom());
        return "choix-quiz"; 
    }

   
    @GetMapping("/choix-niveau/{categorie}")
    public String afficherChoixNiveau(@PathVariable String categorie, HttpSession session, Model model) {
        if (session.getAttribute("utilisateurConnecte") == null) return "redirect:/";
        
        model.addAttribute("categorie", categorie);
        return "choix-niveau"; 
    }

    
    @GetMapping("/quiz/{categorie}/{niveau}")
    public String demarrerQuiz(@PathVariable String categorie, 
                               @PathVariable int niveau, 
                               HttpSession session, 
                               Model model) {
        if (session.getAttribute("utilisateurConnecte") == null) return "redirect:/";

        List<Question> questions = questionService.obtenirQuestionsParCategorieEtNiveau(categorie, niveau);
        
        model.addAttribute("questions", questions);
        model.addAttribute("nomQuiz", categorie);
        model.addAttribute("niveauActuel", niveau); 
        
        return "page-quiz";
    }

    
    @PostMapping("/quiz/soumettre")
    public String soumettre(HttpServletRequest request, 
                            @RequestParam String categorieQuiz,
                            @RequestParam int niveauQuiz, 
                            HttpSession session, 
                            Model model) {
        
        if (session.getAttribute("utilisateurConnecte") == null) return "redirect:/";

       
        List<Question> questions = questionService.obtenirQuestionsParCategorieEtNiveau(categorieQuiz, niveauQuiz);
        
        
        Map<String, Object> rezilta = questionService.analyserResultats(questions, request, niveauQuiz);

       
        model.addAllAttributes(rezilta);
        
        
        model.addAttribute("categorieQuiz", categorieQuiz);
        model.addAttribute("niveauActuel", niveauQuiz);
        
        return "resultat";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/";
    }
}