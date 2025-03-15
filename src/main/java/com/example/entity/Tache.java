package com.example.entity;

import com.example.enums.StatutTache;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "taches")
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre de la tâche est obligatoire")
    @Size(min = 3, max = 100, message = "Le titre doit contenir entre 3 et 100 caractères")
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    @Enumerated(EnumType.STRING)
    private StatutTache statut;

    @ManyToOne
    @JoinColumn(name = "developpeur_id")
    private User developpeur;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;
}
