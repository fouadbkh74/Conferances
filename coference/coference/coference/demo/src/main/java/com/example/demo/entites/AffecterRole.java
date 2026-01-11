
package com.example.demo.entites;

import com.example.demo.Enumeration.*;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AffecterRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private UserApp user;

    @ManyToOne
    @JoinColumn(name = "conference_id", nullable = false)
    private Conference conference;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole role;


    public AffecterRole() {}

    public AffecterRole(UserApp user, Conference conference, ERole role) {
        this.user = user;
        this.conference = conference;
        this.role = role;
    }


}
