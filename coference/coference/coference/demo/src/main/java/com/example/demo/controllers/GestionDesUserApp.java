package com.example.demo.controllers;


import com.example.demo.Exception.*;
import com.example.demo.Services.interfaces.IUserAppService;
import com.example.demo.entites.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class GestionDesUserApp {

    @Autowired
    private IUserAppService userAppService;

    // Ajouter un utilisateur
    @PostMapping
    public ResponseEntity<UserApp> addUser(@RequestBody UserApp userApp) {
        UserApp newUser = userAppService.addUserApp(userApp);
        return ResponseEntity.ok(newUser);
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<UserApp> getUserById(@PathVariable Long id) {
        UserApp user = userAppService.getUserAppById(id);
        return ResponseEntity.ok(user);
    }

    // Récupérer un utilisateur par email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserApp> getUserByEmail(@PathVariable String email) {
        UserApp user = userAppService.getUserAppByEmail(email);
        return ResponseEntity.ok(user);
    }

    // Récupérer tous les utilisateurs
    @GetMapping("/all")
    public ResponseEntity<List<UserApp>> getAllUsers() {
        List<UserApp> users = userAppService.getAllUserApps();
        return ResponseEntity.ok(users);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userAppService.deleteUserApp(id);
        return ResponseEntity.noContent().build();
    }
}
