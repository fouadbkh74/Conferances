package com.example.demo.controllers;

import com.example.demo.Enumeration.ERole;
import com.example.demo.Services.implementation.ConferenceService;
import com.example.demo.Services.implementation.EvaluationService;
import com.example.demo.Services.implementation.SoumissionService;
import com.example.demo.Services.implementation.UserAppService;
import com.example.demo.entites.Conference;
import com.example.demo.entites.Evaluation;
import com.example.demo.Enumeration.EtatEvaluation;
import com.example.demo.entites.Soumission;
import com.example.demo.entites.UserApp;
import com.example.demo.repositories.UserAppRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class GestionDesEvaluations {

    private  EvaluationService evaluationService;
    private UserAppRepository userAppRepository;
    @Autowired
    private UserAppService userAppService;

    @Autowired
    public GestionDesEvaluations(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private SoumissionService soumissionService;
    // Récupérer une évaluation par son ID
    @GetMapping("/{evaluationId}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Long evaluationId) {
        Evaluation evaluation = evaluationService.findEvaluationById(evaluationId);
        return ResponseEntity.ok(evaluation);
    }

    @PostMapping("/create")
    public ResponseEntity<Evaluation> createEvaluation(
            @RequestBody Evaluation evaluation,  // Détails de l'évaluation
            @RequestParam Long userId,           // ID de l'utilisateur qui crée l'évaluation
            @RequestParam int soumissionId      // ID de la soumission
    ) {
        // Vérifier l'existence de l'utilisateur
        UserApp creator = userAppService.getUserAppById(userId);
        if (creator == null) {
            throw new RuntimeException(" utilisateur n'esxiste pas ! ") ;
        }

        // Vérifier si l'utilisateur a bien le rôle d'évaluateur
        if (!creator.getRoles().contains(ERole.EVALUATEUR)) {
            throw new RuntimeException("Cet utilisateur n'est pas un évaluateur donc n'est pas autorisé à créer une évaluation !");
        }



        // Vérifier l'existence de la soumission
        Soumission soumission = soumissionService.getSoumissionById(soumissionId);
        if (soumission == null) {
            throw new RuntimeException("Soumission non trouvée");
        }

        // Associer la soumission à l'évaluation
        evaluation.setSoumission(soumission);


        // Sauvegarder l'évaluation
        Evaluation createdEvaluation = evaluationService.createEvaluation(evaluation);

        // Retourner une réponse de succès
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvaluation);
    }

    @PostMapping("/{evaluationId}/submit")
    public ResponseEntity<Evaluation> submitEvaluation(
            @PathVariable Long evaluationId,       // Utilisation de @PathVariable pour obtenir l'ID de l'évaluation
            @RequestBody Evaluation newEvaluation, // Utilisation de @RequestBody pour récupérer les informations de l'évaluation à soumettre
            @RequestParam Long id                 // Utilisation de @RequestParam pour obtenir l'ID de l'évaluateur
    ) {
        // Récupérer l'utilisateur évaluateur à partir de l'ID
        UserApp evaluator = userAppRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec ID: " + id));

        // Vérifier si l'utilisateur a bien le rôle d'évaluateur
        if (!evaluator.getRoles().contains(ERole.EVALUATEUR)) {
            throw new RuntimeException("Cet utilisateur n'est pas un évaluateur valide!");
        }

        // Récupérer l'évaluation par son ID
        Evaluation evaluation = evaluationService.findEvaluationById(evaluationId);
        if (evaluation == null) {
            throw new RuntimeException("Évaluation non trouvée avec l'ID: " + evaluationId);
        }

        // Mettre à jour l'évaluation avec les nouvelles données
        evaluation.setNote(newEvaluation.getNote());
        evaluation.setCommentaire(newEvaluation.getCommentaire());
        evaluation.setEtat(newEvaluation.getEtat()); // Mise à jour du statut de l'évaluation

        // Sauvegarder l'évaluation mise à jour
        Evaluation updatedEvaluation = evaluationService.createEvaluation(evaluation);

        // Retourner l'évaluation mise à jour en réponse
        return ResponseEntity.ok(updatedEvaluation);
    }


    // Récupérer toutes les évaluations
    @GetMapping
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.findAllEvaluations();
        return ResponseEntity.ok(evaluations);
    }

    // Pour les éditeurs afin de récupérer les évaluations acceptées
    @GetMapping("/valides")
    public ResponseEntity<List<Evaluation>> getAcceptedEvaluations() {
        List<Evaluation> acceptedEvaluations = evaluationService.findEvaluationsByEtat(EtatEvaluation.VALIDEE);
        return ResponseEntity.ok(acceptedEvaluations);
    }
}
