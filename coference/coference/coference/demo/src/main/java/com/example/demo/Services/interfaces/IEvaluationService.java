package com.example.demo.Services.interfaces;

import com.example.demo.Enumeration.EtatEvaluation;
import com.example.demo.entites.Evaluation;

import java.util.List;

public interface IEvaluationService {

    Evaluation findEvaluationById(Long evaluationId);
    Evaluation createEvaluation(Evaluation evaluation);
    List<Evaluation> findAllEvaluations();
    List<Evaluation> findEvaluationsByEtat(EtatEvaluation etat);
}
