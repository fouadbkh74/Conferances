package com.example.demo.repositories;




import com.example.demo.Enumeration.EtatEvaluation;
import com.example.demo.entites.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByEtat(EtatEvaluation etat);

}