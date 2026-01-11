package com.example.demo.Services.implementation;

import com.example.demo.Enumeration.EtatEvaluation;
import com.example.demo.Services.interfaces.IEvaluationService;
import com.example.demo.entites.Evaluation;
import com.example.demo.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationService implements IEvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public Evaluation findEvaluationById(Long evaluationId) {
        // Recherche d'une évaluation par son ID
        Optional<Evaluation> evaluation = evaluationRepository.findById(evaluationId);
        return evaluation.orElseThrow(() -> new RuntimeException("Evaluation not found with ID: " + evaluationId));
    }

    @Override
    public Evaluation createEvaluation(Evaluation evaluation) {
        // Création d'une nouvelle évaluation dans la base de données
        return evaluationRepository.save(evaluation);
    }

    @Override
    public List<Evaluation> findAllEvaluations() {
        // Récupérer toutes les évaluations
        return evaluationRepository.findAll();
    }

    @Override
    public List<Evaluation> findEvaluationsByEtat(EtatEvaluation etat) {
        // Filtrer les évaluations selon leur état
        return evaluationRepository.findByEtat(etat);
    }

    // Vous pouvez ajouter des méthodes supplémentaires ici selon les besoins

    public List<Evaluation> findEvaluationsByStatus(String status) {
        // Exemple : Récupérer des évaluations selon leur statut
        return evaluationRepository.findAll().stream()
                .filter(evaluation -> evaluation.getEtat().name().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}
