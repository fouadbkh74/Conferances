package com.example.demo.repositories;

import com.example.demo.entites.Soumission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoumissionRepository extends JpaRepository<Soumission, Integer> {
}
