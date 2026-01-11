package com.example.demo.Services.interfaces;

import com.example.demo.entites.Conference;
import com.example.demo.entites.UserApp;

import java.util.List;
import java.util.Optional;

public interface IConferenceService {
    List<Conference> getAllConferences();
    Optional<Conference> getConferenceById(int id);
    public Conference createConference(Conference newConference, UserApp creator);   Optional<Conference> updateConference(int id, Conference updatedConference);
    boolean deleteConference(int id);
}
