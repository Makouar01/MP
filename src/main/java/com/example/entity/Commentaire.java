package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commentaires")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le contenu du commentaire est obligatoire")
    @Column(columnDefinition = "TEXT")
    private String contenu;

    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private User auteur;

    @ManyToOne
    @JoinColumn(name = "tache_id", nullable = true)
    private Tache tache;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = true)
    private Test test;
}
