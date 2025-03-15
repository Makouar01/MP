package com.example.service;

import com.example.entity.User;
import com.example.enums.Role;
import com.example.repository.UserRepository;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {



    private final UserRepository userRepository;

    private final FileStorageService fileStorageService;

    public UserService(UserRepository userRepository , FileStorageService fileStorageService){
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }


    // Créer un utilisateur avec une image de profil
    public User createUser(User user, MultipartFile photoProfil) throws IOException {
        if (photoProfil != null && !photoProfil.isEmpty()) {
            String fileName = fileStorageService.storeFile(photoProfil);
            user.setPhotoProfil(fileName);
        }
        return userRepository.save(user);
    }

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Récupérer un utilisateur par son ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Mettre à jour un utilisateur avec une nouvelle image de profil
    public User updateUser(Long id, User userDetails, MultipartFile photoProfil) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
        if(userDetails.getNom()!=null && !userDetails.getNom().isEmpty()){
            user.setNom(userDetails.getNom());
        }
        if(userDetails.getEmail()!=null && !userDetails.getEmail().isEmpty()){
            user.setEmail(userDetails.getEmail());

        }
        if(userDetails.getMotDePasse()!=null && !userDetails.getMotDePasse().isEmpty()){
            user.setMotDePasse(userDetails.getMotDePasse());
        }
        if(userDetails.getRole()!=null &&  EnumUtils.isValidEnum(Role.class, userDetails.getRole().name())){
            user.setRole(userDetails.getRole());
        }


        if (photoProfil != null && !photoProfil.isEmpty()) {
            String fileName = fileStorageService.storeFile(photoProfil);
            user.setPhotoProfil(fileName);
        }

        return userRepository.save(user);
    }

    // Supprimer un utilisateur
    public void deleteUser(Long id) {
        User user  = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found !"));
        userRepository.delete(user);
    }
    public String getUserProfilePhoto(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
        return user.getPhotoProfil(); // retourne le nom du fichier de la photo de profil
    }
}