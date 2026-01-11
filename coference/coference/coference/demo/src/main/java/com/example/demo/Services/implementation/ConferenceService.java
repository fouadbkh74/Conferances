package com.example.demo.Services.implementation;

import com.example.demo.Services.interfaces.IConferenceService;
import com.example.demo.entites.*;
import com.example.demo.Enumeration.*;
import com.example.demo.Services.implementation.*;
import com.example.demo.entites.Conference;
import com.example.demo.repositories.ConferenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService implements IConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private AffecterRoleService affecterRoleService;

    @Override
    public List<Conference> getAllConferences() {
        return (List<Conference>) conferenceRepository.findAll();
    }

    @Override
    public Optional<Conference> getConferenceById(int id) {
        return conferenceRepository.findById(id);
    }


    @Override
    @Transactional
    public Conference createConference(Conference newConference, UserApp creator) {
        // Créer une instance vide
        Conference conference = new Conference();

        // Définir les propriétés manuellement
        conference.setTitre(newConference.getTitre());
        conference.setThematique(newConference.getThematique());
        conference.setDateDebut(newConference.getDateDebut());
        conference.setDateFin(newConference.getDateFin());
        conference.setEtat(Etatconference.OUVERTE); // Par défaut

        // Sauvegarder la conférence
        Conference savedConference = conferenceRepository.save(conference);

        // Assigner le rôle "EDITEUR" au créateur
        affecterRoleService.affecterRole(creator, savedConference, ERole.EDITEUR);

        return savedConference;
    }

    @Override
    public Optional<Conference> updateConference(int id, Conference updatedConference) {

        return conferenceRepository.findById(id).map(existingConference -> {
            existingConference.setTitre(updatedConference.getTitre());
            existingConference.setDateDebut(updatedConference.getDateDebut());
            existingConference.setDateFin(updatedConference.getDateFin());
            existingConference.setThematique(updatedConference.getThematique());
            existingConference.setEtat(updatedConference.getEtat());
            return conferenceRepository.save(existingConference);
        });
    }

    @Override
    public boolean deleteConference(int id) {
        if (conferenceRepository.existsById(id)) {
            conferenceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
