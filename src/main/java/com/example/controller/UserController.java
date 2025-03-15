package com.example.controller;

import com.example.entity.User;
import com.example.service.FileStorageService;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final FileStorageService fileStorageService;

    public UserController(UserService userService,FileStorageService fileStorageService){
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    // Créer un utilisateur avec une image de profil
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<User> createUser(@RequestParam("user") String userJson,
                           @RequestParam(value = "photoProfil", required = false) MultipartFile photoProfil) throws IOException {

        // Convertir le JSON en objet User
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(userJson, User.class);
        User createdUser = userService.createUser(user,photoProfil);
        return new  ResponseEntity<>(createdUser,HttpStatus.CREATED);


    }


    // Récupérer tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<User>>  getAllUsers() {
        List<User>  users = userService.getAllUsers();
        return new ResponseEntity<>(users , HttpStatus.OK);
    }

    // Récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mettre à jour un utilisateur avec une nouvelle image de profil
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestParam("user") String userJson,
                                           @RequestParam(value = "photoProfil", required = false) MultipartFile photoProfil) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        User userDetails = objectMapper.readValue(userJson, User.class);

        User updatedUser = userService.updateUser(id, userDetails, photoProfil);
        return ResponseEntity.ok(updatedUser);
    }


    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}





