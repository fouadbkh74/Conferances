package com.example.demo.Services.implementation;

import com.example.demo.Services.interfaces.IAffecterRole;
import com.example.demo.Enumeration.*;
import com.example.demo.entites.*;
import com.example.demo.repositories.AffecterRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffecterRoleService implements IAffecterRole {

    @Autowired
    private AffecterRoleRepository affecterRoleRepository;

    // Implémentation de la méthode pour affecter un rôle à un utilisateur dans une conférence
    @Override
    public void affecterRole(UserApp user, Conference conference, ERole role) {
        // Créer une nouvelle instance d'AffecterRole avec les paramètres fournis
        AffecterRole affecterRoleEntity = new AffecterRole(user, conference, role);

        // Sauvegarder l'entité dans la base de données
        affecterRoleRepository.save(affecterRoleEntity);
    }
    @Override
    public boolean ARole(UserApp user, Conference conference, ERole role) {
        //implementer
        return false;
    }

}
