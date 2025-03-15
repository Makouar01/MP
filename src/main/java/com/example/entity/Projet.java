package com.example.entity;

import com.example.enums.StatutProjet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projets")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du projet est obligatoire")
    @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
    private String nom;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    @Enumerated(EnumType.STRING)
    private StatutProjet statut;

    @OneToOne
    @JoinColumn(name = "chef_equipe_id")
    private User chefEquipe;

    @ManyToMany
    @JoinTable(
            name = "projet_users",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> membres = new HashSet<>();

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    private Set<Tache> taches = new HashSet<>();
}
