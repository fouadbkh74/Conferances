package com.example.demo.entites;

import com.example.demo.Enumeration.EtatEvaluation;
import jakarta.persistence.*;
import com.example.demo.entites.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Utilisation de Long au lieu de int

    private String commentaire; // Renommé 'description' en 'commentaire' pour plus de clarté

    @Enumerated(EnumType.STRING)
    private EtatEvaluation etat;

    private int note; // Ajout d'une note sur 10


    @ManyToOne
    private Soumission soumission; // La soumission évaluée


    @ManyToOne
    @JoinColumn(nullable = false)
    private UserApp evaluateur;




}
