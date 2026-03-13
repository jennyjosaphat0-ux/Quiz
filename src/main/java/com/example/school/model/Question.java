/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.school.model;

/**
 *
 * @author jenny
 */

import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categorie; 
    private String enonce;    

    private int niveau;     

    @Column(name = "optiona")
    private String optionA;

    @Column(name = "optionb")
    private String optionB;

    @Column(name = "optionc")
    private String optionC;

    @Column(name = "optiond")
    private String optionD;

    @Column(name = "reponse_correcte")
    private String reponseCorrecte;

   
    public Long getId() { return id; }
    public String getCategorie() { return categorie; }
    public String getEnonce() { return enonce; }
    public int getNiveau() { return niveau; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public String getReponseCorrecte() { return reponseCorrecte; }

    
    public void setId(Long id) { this.id = id; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setEnonce(String enonce) { this.enonce = enonce; }
    public void setNiveau(int niveau) { this.niveau = niveau; }
    public void setOptionA(String optionA) { this.optionA = optionA; }
    public void setOptionB(String optionB) { this.optionB = optionB; }
    public void setOptionC(String optionC) { this.optionC = optionC; }
    public void setOptionD(String optionD) { this.optionD = optionD; }
    public void setReponseCorrecte(String reponseCorrecte) { this.reponseCorrecte = reponseCorrecte; }
}
