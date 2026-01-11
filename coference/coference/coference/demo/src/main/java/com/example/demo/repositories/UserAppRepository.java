package com.example.demo.repositories;

import com.example.demo.entites.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Long> {
   Optional<UserApp> findByEmail(String email);

}
