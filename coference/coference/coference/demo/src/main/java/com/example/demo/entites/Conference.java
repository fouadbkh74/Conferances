package com.example.demo.entites;



import com.example.demo.Enumeration.*;


import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@Builder
@Entity
@AllArgsConstructor
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titre;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateFin;

    @Column(nullable = false)
    private String thematique;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Etatconference Etat;


    @OneToMany(mappedBy = "conferance", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Soumission> soumissions = new ArrayList<>();


    public Conference() {
    }


}



