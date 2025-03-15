package com.example.entity;

import com.example.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom doit avoir entre 3 et 50 caractères")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit avoir au moins 6 caractères")
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 255)
    private String photoProfil; // Stocke le chemin relatif de l'image sur le serveur

    @ManyToMany(mappedBy = "membres")
    private Set<Projet> projets = new HashSet<>();
}