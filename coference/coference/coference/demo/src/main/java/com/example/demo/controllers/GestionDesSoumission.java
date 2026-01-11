package com.example.demo.controllers;

import com.example.demo.Services.interfaces.ISoumissionService;
import com.example.demo.entites.Soumission;
import com.example.demo.entites.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soumissions")
public class GestionDesSoumission {

    @Autowired
    private ISoumissionService soumissionService;
    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    ConferenceRepository conferenceRepository;



    // Récupérer toutes les soumissions
    @GetMapping
    public ResponseEntity<List<Soumission>> getAllSoumissions() {
        List<Soumission> soumissions = soumissionService.getAllSoumissions();
        return ResponseEntity.ok(soumissions);

    }

    // Récupérer une soumission par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSoumissionById(@PathVariable int id) {
        Soumission soumission = soumissionService.getSoumissionById(id);
        if (soumission == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Soumission avec l'ID " + id + " n'existe pas !");
        }
        return ResponseEntity.ok(soumission);
    }

    // Mettre à jour une soumission existante
    @PutMapping("/{id}")
    public ResponseEntity<Soumission> updateSoumission(@PathVariable int id, @RequestBody Soumission soumissionDetails) {
        Soumission updatedSoumission = soumissionService.updateSoumission(id, soumissionDetails);
        return ResponseEntity.ok(updatedSoumission);
    }

    @PostMapping("/add")
    public ResponseEntity<Soumission> createSoumission(@RequestBody Soumission newSoumission,
                                                       @RequestParam Long creatorId,
                                                       @RequestParam int conferenceid) {
        // Récupérer le créateur par ID
        UserApp creator = userAppRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + creatorId));

        // Récupérer la conférence par ID
        Conference conference = conferenceRepository.findById(conferenceid)
                .orElseThrow(() -> new RuntimeException("Conférence non trouvée avec l'ID : " + conferenceid));

        // Créer la soumission via le service
        Soumission soumission = soumissionService.createSoumission(newSoumission, creator, conference);

        // Retourner la soumission créée avec un statut HTTP 200
        return ResponseEntity.ok(soumission);
    }


    // Supprimer une soumission par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoumission(@PathVariable int id) {
        soumissionService.deleteSoumission(id);
        return ResponseEntity.noContent().build();
    }
}
