package com.example.demo.Services.implementation;

import com.example.demo.Enumeration.ERole;
import com.example.demo.Enumeration.EtatEvaluation;
import com.example.demo.Services.interfaces.ISoumissionService;
import com.example.demo.entites.*;
import com.example.demo.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

@Service
public class SoumissionService implements ISoumissionService {

    @Autowired
    private SoumissionRepository soumissionRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    public EvaluationRepository evaluationRepository;

    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    private AffecterRoleRepository affecterRoleRepository;

    @Override
    public Soumission addSoumission(Soumission soumission) {
        return soumissionRepository.save(soumission);
    }
    @Override
    @Transactional
    public Soumission createSoumission(Soumission newSoumission, UserApp creator, Conference conference) {

        // Vérifier et récupérer la conférence associée
        if (conference == null || conference.getId() == 0) {
            throw new RuntimeException("La conférence doit être spécifiée et posséder un ID valide.");
        }

        // Associer la conférence à la soumission
        newSoumission.setConferance(conference);

        // Vérifier si le créateur a déjà le rôle AUTEUR
        boolean roleExists = affecterRoleRepository.existsByUserIdAndConferenceIdAndRole(
                creator.getId(), conference.getId(), ERole.AUTEUR);

        if (!roleExists) {
            // Ajouter le rôle AUTEUR à l'utilisateur
            AffecterRole auteurRole = new AffecterRole(creator, conference, ERole.AUTEUR);
            affecterRoleRepository.save(auteurRole); // Sauvegarder le rôle dans la base de données
        }

        // Assigner l'utilisateur comme auteur de la soumission
        List<UserApp> authors = new ArrayList<>();
        authors.add(creator);
        newSoumission.setAuteurs(authors);

        // Sauvegarder la soumission
        return soumissionRepository.save(newSoumission);
    }



    @Override
    public Soumission getSoumissionById(int id) {
        return soumissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soumission not found with ID: " + id));
    }

    @Override
    public List<Soumission> getAllSoumissions() {
        return new ArrayList<>(soumissionRepository.findAll());
    }

    @Override
    public Soumission updateSoumission(int id, Soumission soumissionDetails) {
        Soumission existingSoumission = soumissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soumission not found with ID: " + id));

        // Mise à jour des champs
        existingSoumission.setNom(soumissionDetails.getNom());
        existingSoumission.setDescription(soumissionDetails.getDescription());
        existingSoumission.setDetailsSoumission(soumissionDetails.getDetailsSoumission());
        existingSoumission.setConferance(soumissionDetails.getConferance());

        return soumissionRepository.save(existingSoumission);
    }

    @Override
    public void deleteSoumission(int id) {
        Soumission existingSoumission = soumissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soumission not found with ID: " + id));
        soumissionRepository.delete(existingSoumission);
    }

    @Override
    public void assignSubmissionToEvaluator(int submissionId, Long evaluatorId, Long editorId) {
        // Récupérer la soumission depuis la base de données
        Soumission soumission = soumissionRepository.findById(submissionId).orElse(null);
        if (soumission == null) {
            System.out.println("Soumission non trouvée avec l'id: " + submissionId);
            return; // Arrêter l'exécution si la soumission n'est pas trouvée
        }

        // Récupérer l'évaluateur depuis la base de données
        UserApp evaluateur = userAppRepository.findById(evaluatorId).orElse(null);
        if (evaluateur == null) {
            System.out.println("Évaluateur non trouvé avec l'id: " + evaluatorId);
            return; // Arrêter l'exécution si l'évaluateur n'est pas trouvé
        }

        // Récupérer l'éditeur depuis la base de données
        UserApp editeur = userAppRepository.findById(editorId).orElse(null);
        if (editeur == null) {
            System.out.println("Éditeur non trouvé avec l'id: " + editorId);
            return; // Arrêter l'exécution si l'éditeur n'est pas trouvé
        }

        // Vérifier que l'éditeur a bien le rôle "EDITEUR" pour cette conférence
        boolean isEditor = affecterRoleRepository.existsByUserIdAndConferenceIdAndRole(
                editorId,
                soumission.getConferance().getId(),
                ERole.EDITEUR
        );
        if (!isEditor) {
            System.out.println("L'utilisateur n'est pas autorisé en tant qu'éditeur pour cette conférence.");
            return; // Arrêter l'exécution si l'éditeur n'est pas autorisé
        }

        // Vérifier que l'évaluateur n'est pas l'auteur de la soumission
        if (soumission.getAuteurs().contains(evaluateur)) {
            System.out.println("L'évaluateur ne peut pas évaluer une soumission qu'il a soumise.");
            return; // Arrêter l'exécution si l'évaluateur est l'auteur de la soumission
        }

        // Créer une évaluation avec des valeurs par défaut
        Evaluation evaluation = new Evaluation();
        evaluation.setSoumission(soumission);
        evaluation.setEvaluateur(evaluateur);
        evaluation.setEtat(EtatEvaluation.EN_ATTENTE);  // Statut "En attente"
        evaluation.setNote(NULL);  // Score initialisé à null
        evaluation.setCommentaire("Évaluation en attente");  // Commentaire par défaut

        // Sauvegarder l'évluation
        Evaluation savedEvaluation = evaluationRepository.save(evaluation);

        // Ajouter l'évaluation à la soumission et sauvegarder
        soumission.getEvaluations().add(savedEvaluation);
        soumissionRepository.save(soumission);
    }


}
