package com.example.entity;

import com.example.enums.StatutTest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La description du test est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    @Enumerated(EnumType.STRING)
    private StatutTest statut;

    @ManyToOne
    @JoinColumn(name = "responsable_test_id")
    private User responsableTest;

    @ManyToOne
    @JoinColumn(name = "tache_id")
    private Tache tache;
}
