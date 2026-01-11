package com.example.demo.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;



@Data
@Entity
@AllArgsConstructor
public class UserApp {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;



        private String firstName;
        private String lastName;

        @Column(unique = true)
        private String email;

        private String password;


        @OneToMany(fetch = FetchType.EAGER)
        private List<Soumission> submissions;

        @OneToMany(fetch = FetchType.EAGER)
        private List<Evaluation> evaluations;

        @OneToMany(fetch = FetchType.EAGER)
        private List<AffecterRole> roles = new ArrayList<>();


        public UserApp() {}

        public UserApp(Long id, String firstName, String lastName, String email, String password) {
                this.id = id;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.password = password;
        }


}
