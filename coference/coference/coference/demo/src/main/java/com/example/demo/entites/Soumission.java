package com.example.demo.entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.example.demo.entites.*;

import java.util.ArrayList;
import java.util.List;


@Data

@Entity
@AllArgsConstructor
public class Soumission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;


    // Liste des auteurs (UserApp)
    @ManyToMany
    @JoinTable(
            name = "soumission_auteurs",
            joinColumns = @JoinColumn(name = "soumission_id"),
            inverseJoinColumns = @JoinColumn(name = "user_app_id"))
    private List<UserApp> auteurs;

    @OneToOne(cascade = CascadeType.ALL)
    private DetailsSoumission detailsSoumission;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conferance;

    @OneToMany
    private   List<Evaluation> evaluations=new ArrayList<>();

    public Soumission() {}










}
