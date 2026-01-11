package com.example.demo.Services.interfaces;

import com.example.demo.entites.Soumission;
import com.example.demo.entites.*;

import java.util.List;

public interface ISoumissionService {
    Soumission addSoumission(Soumission soumission);
    Soumission getSoumissionById(int id);
    List<Soumission> getAllSoumissions();
    Soumission createSoumission(Soumission newsoumission,  UserApp createur ,Conference conference);
    Soumission updateSoumission(int id, Soumission soumissionDetails);
    void deleteSoumission(int id);
    public void assignSubmissionToEvaluator(int submissionId, Long evaluatorId, Long editorId) ;

    }
