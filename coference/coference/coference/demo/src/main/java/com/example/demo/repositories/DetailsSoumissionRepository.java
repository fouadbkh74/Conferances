package com.example.demo.repositories;



import com.example.demo.entites.DetailsSoumission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsSoumissionRepository extends CrudRepository<DetailsSoumission, Integer> {
}
