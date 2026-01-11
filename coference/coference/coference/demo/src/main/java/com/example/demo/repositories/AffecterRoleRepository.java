package com.example.demo.repositories;

import com.example.demo.entites.AffecterRole;
import com.example.demo.Enumeration.ERole;  // Assurez-vous d'utiliser la bonne enum ici
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffecterRoleRepository extends JpaRepository<AffecterRole, Long> {

    // Méthode pour vérifier si un AffecterRole avec un userId, conferenceId et role spécifiques existe
    boolean existsByUserIdAndConferenceIdAndRole(Long userId, int conferenceId, ERole role);
}
