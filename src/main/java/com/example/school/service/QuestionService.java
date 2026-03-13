package com.example.school.service;

import com.example.school.model.Question;
import com.example.school.repository.QuestionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    private static final int TAN_LIMITE = 200;

    public List<Question> obtenirQuestionsParCategorieEtNiveau(String categorie, int niveau) {
        return questionRepository.findByCategorieAndNiveau(categorie, niveau);
    }

    public Map<String, Object> analyserResultats(List<Question> questions, HttpServletRequest request, int niveauActuel) {
        int score = 0;
        int total = questions.size();
        HttpSession session = request.getSession();

       
        String tanPranStr = request.getParameter("seconds_taken");
        int tanPran = (tanPranStr != null) ? Integer.parseInt(tanPranStr) : 0;
        boolean tanDepase = (tanPran >= TAN_LIMITE);

        
        if (!tanDepase) {
            for (Question q : questions) {
                String reponse = request.getParameter("q" + q.getId());
                if (reponse != null && reponse.equalsIgnoreCase(q.getReponseCorrecte())) {
                    score++;
                }
            }
        }

        
        session.setAttribute("scoreNiveau" + niveauActuel, score);

        Map<String, Object> bilan = new HashMap<>();
        bilan.put("score", score);
        bilan.put("total", total);
        bilan.put("niveauActuel", niveauActuel);
        bilan.put("tanPran", tanPran);
        bilan.put("tanDepase", tanDepase);

        boolean aReussi = (score >= (total / 2) && !tanDepase);
        bilan.put("aReussi", aReussi);
        bilan.put("peutPasser", aReussi && niveauActuel < 3);
        bilan.put("prochainNiveau", niveauActuel + 1);

        
        if (niveauActuel == 3 && aReussi) {
            Integer s1 = (Integer) session.getAttribute("scoreNiveau1");
            Integer s2 = (Integer) session.getAttribute("scoreNiveau2");
            s1 = (s1 == null) ? 0 : s1;
            s2 = (s2 == null) ? 0 : s2;

            double moyenne = (s1 + s2 + score) / 3.0;
            bilan.put("isFinal", true);
            bilan.put("s1", s1);
            bilan.put("s2", s2);
            bilan.put("s3", score);
            bilan.put("moyenne", String.format("%.2f", moyenne));
        } else {
            bilan.put("isFinal", false);
        }

        return bilan;
    }
}